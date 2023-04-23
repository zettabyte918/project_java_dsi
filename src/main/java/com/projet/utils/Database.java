package com.projet.utils;

// import sql library
import java.sql.*;

import com.projet.utils.config.ConfigManager;

public class Database {
    // Set up database connection parameters
    private static Database instance;

    private String dbName = ConfigManager.getDbName();
    private String url = ConfigManager.getDbUrl();
    private String username = ConfigManager.getDbUsername();
    private String password = ConfigManager.getDbPassword();
    private Connection connection;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();

            // Create a database connection
            instance.connection = instance.getDBConnection();

        }
        return instance;
    }

    public ResultSet query(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public TestDBResponse TestConnection() {
        try {
            // Check if the connection is valid
            if (connection.isValid(5)) { // 5 seconds timeout
                return new TestDBResponse(1, "Connected successfully to " + username + ":" + dbName);
            } else {
                return new TestDBResponse(0, "Failed to connect to the database to: " + dbName);
            }

        } catch (Exception e) {
            // TODO: handle exception
            return new TestDBResponse(0, "Failed to connect to the database to: " + dbName);
        }

    }

    public Connection getDBConnection() {
        try {
            return DriverManager.getConnection(this.url + this.dbName, this.username, this.password);
        } catch (Exception e) {
            return null;
        }
    }

    public String getDbName() {
        return dbName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
