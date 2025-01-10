package ink.fsp.playerMonitor.database;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.ResultItem.PlayerItem;
import ink.fsp.playerMonitor.database.ResultItem.RegionItem;
import ink.fsp.playerMonitor.database.ResultItem.TrackerItem;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class DatabaseManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    private static final String DATABASE_URL = "jdbc:sqlite:PM.db";

    static {
        try {
            // 显式加载 SQLite JDBC 驱动
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        LOGGER.info("Database Initializing");
        setupDatabase();
    }

    public static void setupDatabase() {
        Connection connection;
        try { // 待优化
            connection = DriverManager.getConnection(DATABASE_URL);
            createTrackerTable(connection);
            createPlayersTable(connection);
            createRegionsTable(connection);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("Database Initialed");
    }

    private static boolean createTrackerTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE tracker (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "player_name    VARCHAR NOT NULL," +
                    "region_name    VARCHAR NOT NULL, " +
                    "X NUMERIC NOT NULL, " +
                    "Y NUMERIC NOT NULL, " +
                    "Z NUMERIC NOT NULL, " +
                    "dimension VARCHAR NOT NULL, " +
                    "datetime INTEGER NOT NULL" +
                    ");");
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean createPlayersTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table players (" +
                    "    id             INTEGER primary key autoincrement," +
                    "    playername     VARCHAR NOT NULL," +
                    "    player_uuid    VARCHAR NOT NULL," +
                    "    X              NUMERIC," +
                    "    Y              NUMERIC," +
                    "    Z              NUMERIC," +
                    "    first_join_datetime INTEGER," +
                    "    last_join_datetime  INTEGER," +
                    "    last_leave_datetime  INTEGER" +
                    ");");
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean createRegionsTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table regions (" +
                    "id              INTEGER primary key autoincrement," +
                    "region_name     VARCHAR NOT NULL," +
                    "created_by      VARCHAR NOT NULL," +
                    "create_datetime INTEGER NOT NULL," +
                    "dimension       VARCHAR NOT NULL," +
                    "start_x         NUMERIC," +
                    "start_y         NUMERIC," +
                    "start_z         NUMERIC," +
                    "end_x           NUMERIC," +
                    "end_y           NUMERIC," +
                    "end_z           NUMERIC" +
                    ");");
            statement.close();
            LOGGER.info("regions 创建成功");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static int insertTrackn(String playerName, String regionName, double x, double y, double z, String dimension, Date datetime) {
        String sql = "INSERT INTO tracker (player_name, region_name, x, y, z, dimension, datetime) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            statement.setString(2, regionName);
            statement.setDouble(3, x);
            statement.setDouble(4, y);
            statement.setDouble(5, z);
            statement.setString(6, dimension);
            statement.setLong(7, datetime.getTime());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static ArrayList<TrackerItem> selectTrackn(String playerName, String regionName, Date currentTime, Date targetTime) {
        String sql = "SELECT player_name, region_name, x, y, z, dimension, datetime FROM tracker WHERE player_name = ? AND region_name = ? AND datetime BETWEEN ? AND ?";
        ArrayList<TrackerItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            statement.setString(2, regionName);
            statement.setDouble(3, targetTime.getTime());
            statement.setDouble(4, currentTime.getTime());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        TrackerItem.getTracknItem(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getDouble(3),
                                resultSet.getDouble(4),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                new Date(resultSet.getLong(7))
                        )
                );
            }
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public static int insertPlayers(PlayerItem playerItem) {
        String sql = "INSERT INTO players (playername, player_uuid, x, y, z, first_join_datetime, last_join_datetime, last_leave_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerItem.playername);
            statement.setString(2, playerItem.uuid.toString());
            statement.setDouble(3, playerItem.x);
            statement.setDouble(4, playerItem.y);
            statement.setDouble(5, playerItem.z);
            statement.setLong(6, playerItem.firstJoinDatetime.getTime());
            statement.setLong(7, playerItem.lastJoinDatetime.getTime());
            statement.setLong(8, playerItem.lastLeaveDatetime.getTime());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static ArrayList<PlayerItem> selectPlayers() {
        String sql = "SELECT * FROM players";
        ArrayList<PlayerItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        PlayerItem.getPlayerItem(
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getDouble(5),
                                resultSet.getDouble(6),
                                new Date(resultSet.getLong(7)),
                                new Date(resultSet.getLong(8)),
                                new Date(resultSet.getLong(9))
                        )
                );
            }
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return result;
        }
    }

    public static int UpdatePlayerLastByUuid(UUID playerUuid, double x, double y, double z, Date lastDatetime) {
        String sql = "UPDATE players SET x=?, y=?, z=?, last_leave_datetime=? WHERE player_uuid=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, x);
            statement.setDouble(2, y);
            statement.setDouble(3, z);
            statement.setLong(4, lastDatetime.getTime());
            statement.setString(5, playerUuid.toString());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static int UpdatePlayerNameByUuid(String playerName, UUID playerUuid, Date lastDatetime) {
        String sql = "UPDATE players SET playername=?, first_join_datetime=? WHERE player_uuid=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            statement.setLong(2, lastDatetime.getTime());
            statement.setString(3, playerUuid.toString());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static int queryPlayersName(ArrayList<String> playersName) {
        String sql = "SELECT playername FROM players;";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playersName.add(resultSet.getString(1));
            }
            statement.close();
            return playersName.size();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static int insertRegion(RegionItem regionItem) {
        String sql = "INSERT INTO regions (" +
                "region_name, created_by, create_datetime, dimension, start_x, start_y, start_z, end_x, end_y, end_z)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, regionItem.regionName);
            statement.setString(2, regionItem.createdBy);
            statement.setLong(3, regionItem.createdOn.getTime());
            statement.setString(4, regionItem.world);
            statement.setDouble(5, regionItem.start.x);
            statement.setDouble(6, regionItem.start.y);
            statement.setDouble(7, regionItem.start.z);
            statement.setDouble(8, regionItem.end.x);
            statement.setDouble(9, regionItem.end.y);
            statement.setDouble(10, regionItem.end.z);
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static ArrayList<RegionItem> selectRegions() {
        String sql = "SELECT * FROM regions";
        ArrayList<RegionItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        RegionItem.getRegionItem(
                                new Vec3d(
                                        resultSet.getDouble(6),
                                        resultSet.getDouble(7),
                                        resultSet.getDouble(8)
                                ),
                                new Vec3d(
                                        resultSet.getDouble(9),
                                        resultSet.getDouble(10),
                                        resultSet.getDouble(11)
                                ),
                                resultSet.getString(5),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4)
                        )
                );
            }
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return result;
        }
    }
}
