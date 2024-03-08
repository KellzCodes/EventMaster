import config.DatabaseConfig;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        // Initialize database connection
        Connection connection = DatabaseConfig.getConnection();

        if (connection != null) {
            System.out.println("Successfully connected to the database.");
            // Perform database operations
        } else {
            System.out.println("Failed to connect to the database.");
        }

        // Close the connection when done
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
