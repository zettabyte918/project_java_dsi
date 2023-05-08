package com.projet.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.projet.utils.Database;

public class User {
    private int id;
    private String username;
    private String tel;

    public User(int id, String username, String tel) {
        this.id = id;
        this.username = username;
        this.tel = tel;
    }

    public static boolean Register(String username, String password, String tel) {
        Database db = Database.getInstance();

        try {
            // Check if user already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = db.getDBConnection().prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            int count = checkRs.getInt(1);
            if (count > 0) {
                // User already exists, return false
                return false;
            }

            // User doesn't exist, insert new record
            String query = "INSERT INTO users (username, name, password, tel) VALUES (?, ? , ?, ?)";
            PreparedStatement stmt = db.getDBConnection().prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, tel);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User Login(String username, String password) {
        Database db = Database.getInstance();

        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = db.getDBConnection().prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Login successful
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("tel"));
                return user;
            } else {
                // Login failed
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getConfirmationCode() {
        Database db = Database.getInstance();

        try {
            String query = "SELECT confirmation_code FROM users WHERE id = ?";
            PreparedStatement stmt = db.getDBConnection().prepareStatement(query);
            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("confirmation_code");
            } else {
                return "";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }

    }

    public void setConfirmationCode() {
        Database db = Database.getInstance();
        String code = this.generateCode();

        try {
            String query = "UPDATE users SET confirmation_code= ? WHERE id = ? ";
            PreparedStatement stmt = db.getDBConnection().prepareStatement(query);
            stmt.setString(1, code);
            stmt.setInt(2, this.id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("confirmation_code updated successfully");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String generateCode() {
        String chars = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTel() {
        return tel;
    }

}
