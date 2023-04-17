package com.projet.utils;

// import sql library
import java.sql.*;

public class Db {
    // Set up database connection parameters
    private String dbName = "app";
    private String url = "jdbc:mysql://localhost:3306/" + dbName;
    private String username = "hossem";
    private String password = "Hossem@@@147";

    public Response TestConnection() {

        try {
            // Create a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Check if the connection is valid
            if (connection.isValid(5)) { // 5 seconds timeout
                connection.close();
                return new Response(1, "Connected succesfully to " + username + ":" + dbName);
            } else {
                return new Response(0, "Failed to connect to the database to: " + dbName);

            }

            // Close the connection
        } catch (SQLException e) {
            return new Response(0, "Failed to connect to the database name: " + dbName);
        }
    }
}
