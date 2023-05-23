package com.projet.controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.jfoenix.controls.JFXSlider;
import com.projet.models.Reminder;
import com.projet.utils.Database;
import com.projet.utils.Router;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateReminderController {
    @FXML
    private DatePicker date;

    @FXML
    private JFXSlider hour;

    @FXML
    private TextField message;

    @FXML
    private JFXSlider minute;

    @FXML
    private TextField title;

    private Reminder reminder = null;

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
        this.title.setText(reminder.getTitle());
        this.message.setText(reminder.getMessage());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(reminder.getScheduled_time(), formatter);
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        LocalDate date = dateTime.toLocalDate();
        this.date.setValue(date);
        this.hour.setValue(hour);
        this.minute.setValue(minute);
    }

    @FXML
    void updateReminderData(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        int id = this.reminder.getId();
        String title = this.title.getText();
        String message = this.message.getText();

        LocalDate localDate = date.getValue();
        int selectedHour = (int) hour.getValue();
        int selectedMinute = (int) minute.getValue();

        // Get the current system's time zone
        ZoneId zoneId = ZoneId.systemDefault();

        // Combine the selected date, hour, and minute into a LocalDateTime object
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(selectedHour, selectedMinute));
        // Convert LocalDateTime to Date
        Date dateToStore = Date.from(localDateTime.atZone(zoneId).toInstant());
        this.updateReminder(currentStage, id, title, message, dateToStore);
    }

    public void updateReminder(Stage currentStage, int reminderId, String title, String message,
            Date scheduledTime) {
        Database db = Database.getInstance();

        try {
            // Create the SQL update statement
            String sql = "UPDATE reminders SET title = ?, message = ?, scheduled_time = ? WHERE id = ?";

            // Prepare the statement
            PreparedStatement statement = db.getDBConnection().prepareStatement(sql);

            // Set the parameter values
            statement.setString(1, title);
            statement.setString(2, message);
            statement.setObject(3, new java.sql.Timestamp(scheduledTime.getTime()));
            statement.setInt(4, reminderId);

            // Execute the update statement
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Reminder updated successfully");
                Router.navigateTo(currentStage, "reminders", false);

            } else {
                System.out.println("No reminder found with ID: " + reminderId);
            }

            // Close the statement
            statement.close();
        } catch (SQLException e) {
            // Handle any database-related errors
            e.printStackTrace();
        }
    }

}
