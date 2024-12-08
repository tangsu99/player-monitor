package ink.fsp.playerMonitor.database;

import ink.fsp.playerMonitor.PlayerMonitor;
import ink.fsp.playerMonitor.database.ResultItem.TrackerItem;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

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
                    "playername VARCHAR NOT NULL, " +
                    "X INT, " +
                    "Y INT, " +
                    "Z INT, " +
                    "dimension VARCHAR, " +
                    "datetime INTEGER" +
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
                    "    playername     VARCHAR not null," +
                    "    player_uuid    VARCHAR not null," +
                    "    X              INT," +
                    "    Y              INT," +
                    "    Z              INT," +
                    "    first_datetime INTEGER," +
                    "    last_datetime  INTEGER" +
                    ");");
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static int insertTrackn(String playername, int x, int y, int z, String dimension, Date datetime) {
        String sql = "INSERT INTO tracker (playername, x, y, z, dimension, datetime) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playername);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);
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

    public static ArrayList<TrackerItem> selectTrackn(String playerName) {
        String sql = "SELECT playername, x, y, z, dimension, datetime FROM tracker WHERE playername = ?";
        ArrayList<TrackerItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(
                        TrackerItem.getTracknItem(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getInt(4),
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

    public static int insertPlayers(String playername, int x, int y, int z, Date firstDatetime, Date lastDatetime) {
        String sql = "INSERT INTO players (playername, x, y, z, first_datetime, last_datetime) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playername);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);
            statement.setLong(5, firstDatetime.getTime());
            statement.setLong(6, lastDatetime.getTime());
            int result = statement.executeUpdate();
            statement.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public static int UpdatePlayersByName(String playername, int x, int y, int z, Date lastDatetime) {
        String sql = "UPDATE players SET x=?, y=?, z=?, last_datetime=? WHERE playername=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);
            statement.setLong(4, lastDatetime.getTime());
            statement.setString(5, playername);
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
