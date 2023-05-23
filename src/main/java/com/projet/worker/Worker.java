package com.projet.worker;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.projet.AppState;
import com.projet.models.Reminder;
import com.projet.utils.Database;
import com.projet.utils.OrangeSMS.OrangeSMS;

public class Worker {

    private ScheduledExecutorService executor;

    public void startCheckingReminders() {
        executor = Executors.newSingleThreadScheduledExecutor();
        boolean is_loggedIn = AppState.getInstance().isLoggedIn();
        if (is_loggedIn) {
            int user_id = AppState.getInstance().getUser().getId();
            executor.scheduleAtFixedRate(() -> checkReminders(user_id), 0, 2, TimeUnit.SECONDS);
        }
    }

    public void stopCheckingReminders() {
        executor.shutdown();
    }

    private void checkReminders(int id) {
        Database db = Database.getInstance();
        // System.out.println("hi");
        try {
            Statement statement = db.getDBConnection().createStatement();
            String query = "SELECT * FROM reminders WHERE user_id = " + id
                    + " AND status = 0 AND scheduled_time < NOW()";
            ResultSet resultSet = db.query(query);

            while (resultSet.next()) {
                int reminderId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String message = resultSet.getString("message");
                String scheduledTime = resultSet.getString("scheduled_time");
                int status = resultSet.getInt("status");
                int user_id = resultSet.getInt("user_id");
                System.out.println("Reminder ID: " + reminderId + " Title: " + title + " message: " + message
                        + " Time: " + scheduledTime
                        + " Status: " + status + " user_id: " + user_id);

                // update reminder status
                Reminder.updateReminderStatus(reminderId);
                OrangeSMS.sendReminderSMS(message);
            }

            resultSet.close();
            statement.close();
            db.getDBConnection().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
