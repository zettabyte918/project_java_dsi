package com.projet.models;

// import java.sql.ResultSet;

public class Reminder {
    private int id;
    private String title;
    private String message;
    private String scheduled_time;
    private int status;
    private int user_id;

    // Reminder(ResultSet results) {
    // try {
    // this.id = results.getInt("id");
    // this.title = results.getString("title");
    // this.message = results.getString("message");
    // this.scheduled_time = results.getString("scheduled_time");
    // this.status = results.getInt("status");
    // this.user_id = results.getInt("user_id");

    // } catch (Exception e) {
    // System.out.println("Error extracting reminder info from resultSet");
    // }

    // }

    public Reminder(int id, String title, String message, String scheduled_time, int status, int user_id) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.scheduled_time = scheduled_time;
        this.status = status;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
