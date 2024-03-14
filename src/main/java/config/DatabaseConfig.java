package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public class DatabaseConfig {

    // Database URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/eventmaster";
    // Database credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    /**
     * Attempts to establish a connection to the database.
     * @return A connection to the database.
     * @throws SQLException If a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
}

