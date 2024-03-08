package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                // Load database properties
                InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties");
                Properties properties = new Properties();
                properties.load(inputStream);

                // Get properties
                String driver = properties.getProperty("jdbc.driver");
                String url = properties.getProperty("jdbc.url");
                String user = properties.getProperty("jdbc.user");
                String password = properties.getProperty("jdbc.password");

                // Register JDBC driver
                Class.forName(driver);

                // Open a connection
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}

