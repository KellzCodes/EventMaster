package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 */
public class DatabaseConfig {

    static InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties");
    static Properties properties = new Properties();

    /**
     * Attempts to establish a connection to the database.
     * @return A connection to the database.
     * @throws SQLException If a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {

        String url = null;
        String user = null;
        String password = null;
        try {
            properties.load(inputStream);
            String driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
            // Ensure the JDBC driver is loaded
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url, user, password);
    }
}

