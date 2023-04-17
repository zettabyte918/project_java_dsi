package com.projet.utils;

// import sql library
import java.sql.*;

public class Db {
    // Set up database connection parameters
    private String dbName = ConfigManager.getDbName();
    private String url = ConfigManager.getDbUrl();
    private String username = ConfigManager.getDbUsername();
    private String password = ConfigManager.getDbPassword();

    public TestDBResponse TestConnection() {

        try {
            // Create a database connection
            Connection connection = DriverManager.getConnection(url + dbName, username, password);

            // Check if the connection is valid
            if (connection.isValid(5)) { // 5 seconds timeout
                connection.close();
                return new TestDBResponse(1, "Connected successfully to " + username + ":" + dbName);
            } else {
                return new TestDBResponse(0, "Failed to connect to the database to: " + dbName);

            }

            // Close the connection
        } catch (SQLException e) {
            return new TestDBResponse(0, "Failed to connect to the database name: " + dbName);
        }
    }
}
