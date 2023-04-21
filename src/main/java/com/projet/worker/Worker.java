package com.projet.worker;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.projet.utils.Database;

public class Worker {

    private ScheduledExecutorService executor;

    public void startCheckingReminders() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> checkReminders(2), 0, 2, TimeUnit.SECONDS);
    }

    public void stopCheckingReminders() {
        executor.shutdown();
    }

    private void checkReminders(int id) {
        Database db = Database.getInstance();

        try {

            Statement statement = db.getDBConnection().createStatement();
            String query = "SELECT * FROM reminders WHERE user_id = " + id + " AND status = 0";
            ResultSet resultSet = db.query(query);

            while (resultSet.next()) {
                int reminderId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String scheduledTime = resultSet.getString("scheduled_time");
                int status = resultSet.getInt("status");
                System.out.println("Reminder ID: " + reminderId + " Title: " + title + " Time: " + scheduledTime
                        + " Status: " + status);

            }
            resultSet.close();
            statement.close();
            db.getDBConnection().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
