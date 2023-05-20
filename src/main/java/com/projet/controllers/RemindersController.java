package com.projet.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import com.projet.AppState;
import com.projet.models.Reminder;
import com.projet.utils.Database;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class RemindersController {
    @FXML
    private TableView<Reminder> table;

    @FXML
    private TableColumn<Reminder, Integer> table_id;

    @FXML
    private TableColumn<Reminder, String> table_title;

    @FXML
    private TableColumn<Reminder, String> table_message;

    @FXML
    private TableColumn<Reminder, String> table_date;

    @FXML
    private TableColumn<Reminder, String> table_status;

    public void initialize() {
        // Initialize table columns
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        table_message.setCellValueFactory(new PropertyValueFactory<>("message"));
        table_date.setCellValueFactory(new PropertyValueFactory<>("scheduled_time"));

        table_status.setCellValueFactory(
                (Callback<CellDataFeatures<Reminder, String>, ObservableValue<String>>) new Callback<TableColumn.CellDataFeatures<Reminder, String>, javafx.beans.value.ObservableValue<String>>() {
                    @Override
                    public javafx.beans.value.ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Reminder, String> param) {
                        int status = param.getValue().getStatus();
                        String displayStatus = (status == 0) ? "Pending" : "Completed";
                        return new SimpleStringProperty(displayStatus);
                    }
                });
        table_status.setCellFactory(column -> {
            return new TableCell<Reminder, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
                        setText(item);
                        if (item.equals("Completed")) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.RED);
                        }
                    } else {
                        setText(null);
                    }
                }
            };
        });

        try {
            List<Reminder> reminders = fetchDataFromDatabase();
            table.getItems().addAll(reminders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Reminder> fetchDataFromDatabase() throws SQLException {
        List<Reminder> reminders = new ArrayList<Reminder>();
        Database db = Database.getInstance();
        // int userId = AppState.getInstance().getUser().getId();
        int userId = 2;

        // Connect to the database and retrieve data
        try {

            Statement statement = db.getDBConnection().createStatement();
            String query = "SELECT * FROM reminders WHERE user_id = " + userId;
            ResultSet resultSet = db.query(query);

            while (resultSet.next()) {
                int reminderId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String message = resultSet.getString("message");
                String scheduledTime = resultSet.getString("scheduled_time");
                int status = resultSet.getInt("status");
                int user_id = resultSet.getInt("user_id");

                Reminder reminder = new Reminder(reminderId, title, message, scheduledTime, status, user_id);
                reminders.add(reminder);

            }
            resultSet.close();
            statement.close();
            db.getDBConnection().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("all reminder is: " + reminders.size());
        return reminders;
    }

}
