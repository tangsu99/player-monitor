package ink.fsp.playerMonitor.database;

import ink.fsp.playerMonitor.PlayerMonitor;
import org.slf4j.Logger;

import java.sql.*;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE test (id INTEGER PRIMARY KEY AUTOINCREMENT, playername VARCHAR NOT NULL, X INT, Y INT, Z INT, dimension VARCHAR);");
        } catch (SQLException e) {
            LOGGER.warn("table test already exists");
        }
        LOGGER.info("Database Setup");
    }
    public static int insert(String playername, int x, int y, int z, String dimension) {
        String sql = "INSERT INTO test (playername, x, y, z, dimension) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playername);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);
            statement.setString(5, dimension);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
