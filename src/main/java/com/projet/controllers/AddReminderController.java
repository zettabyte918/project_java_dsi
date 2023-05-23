package com.projet.controllers;

import com.jfoenix.controls.JFXSlider;
import com.projet.AppState;
import com.projet.utils.Database;
import com.projet.utils.Router;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddReminderController {
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

    @FXML
    void add(ActionEvent event) {
        // cuurent window
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        int user_id = AppState.getInstance().getUser().getId();

        String r_title = title.getText();
        String r_message = message.getText();
        LocalDate localDate = date.getValue();
        int selectedHour = (int) hour.getValue();
        int selectedMinute = (int) minute.getValue();

        // Get the current system's time zone
        ZoneId zoneId = ZoneId.systemDefault();

        // Combine the selected date, hour, and minute into a LocalDateTime object
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(selectedHour, selectedMinute));

        // Convert LocalDateTime to Date
        Date dateToStore = Date.from(localDateTime.atZone(zoneId).toInstant());

        try {
            Database db = Database.getInstance();

            // Establish the database connection

            // Create the SQL query with placeholders for the column values
            String query = "INSERT INTO reminders (title, message, scheduled_time, status, user_id) VALUES (?, ?, ?, ?, ?)";

            // Prepare the statement
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);

            // Set the values for the placeholders
            statement.setString(1, r_title);
            statement.setString(2, r_message);
            statement.setTimestamp(3, new java.sql.Timestamp(dateToStore.getTime()));
            statement.setInt(4, 0); // Assuming the default value for status is 0
            statement.setInt(5, user_id);

            // Execute the query

            int affectedRows = statement.executeUpdate();
            System.out.println("affected row: " + affectedRows);

            // Close the statement and connection
            statement.close();

            // go back to reminders window
            Router.navigateTo(currentStage, "reminders", false);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during database operations
        }

    }

}
