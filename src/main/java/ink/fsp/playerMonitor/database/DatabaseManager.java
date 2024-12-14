package ink.fsp.playerMonitor.database;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.ResultItem.PlayerItem;
import ink.fsp.playerMonitor.database.ResultItem.TrackerItem;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class DatabaseManager {
    private static final Logger LOGGER = PlayerMonitor.LOGGER;
    private static final String DATABASE_URL = "jdbc:sqlite:test.db";

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
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            createTrackerTable(connection);
            createPlayersTable(connection);
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
                    "player_uuid    VARCHAR NOT NULL," +
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

    public static int insertTrackn(UUID playerUuid, double x, double y, double z, String dimension, Date datetime) {
        String sql = "INSERT INTO tracker (player_uuid, x, y, z, dimension, datetime) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUuid.toString());
            statement.setDouble(2, x);
            statement.setDouble(3, y);
            statement.setDouble(4, z);
            statement.setString(5, dimension);
            statement.setLong(6, datetime.getTime());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static ArrayList<TrackerItem> selectTrackn(UUID playerUuid) {
        String sql = "SELECT player_uuid, x, y, z, dimension, datetime FROM tracker WHERE player_uuid = ?";
        ArrayList<TrackerItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUuid.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        TrackerItem.getTracknItem(
                                resultSet.getString(1),
                                resultSet.getDouble(2),
                                resultSet.getDouble(3),
                                resultSet.getDouble(4),
                                resultSet.getString(5),
                                new Date(resultSet.getLong(6))
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
}
