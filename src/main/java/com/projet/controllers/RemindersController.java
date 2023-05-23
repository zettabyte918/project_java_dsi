package com.projet.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import com.projet.AppState;
import com.projet.models.Reminder;
import com.projet.utils.Database;
import com.projet.utils.Router;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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

    @FXML
    private Button update_btn;

    @FXML
    void add_reminder(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Router.navigateTo(currentStage, "addreminder", false);
    }

    public void initialize() {
        this.initTbale();
        this.getAllReminders();

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !table.getSelectionModel().isEmpty()) {
                deleteSelectedRow();
                table.refresh();
            }
        });

        update_btn.setOnAction(event -> {
            // current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Reminder selectedReminder = table.getSelectionModel().getSelectedItem();
            if (selectedReminder != null) {
                System.out.println("Update clicked");
                openUpdatePage(currentStage, selectedReminder);

            } else {
                // Show an error message or handle the case when no row is selected
            }
        });

    }

    private void openUpdatePage(Stage currentStage, Reminder reminder) {
        UpdateReminderController controller = Router.navigateTo(currentStage, "updatereminder", false);
        controller.setReminder(reminder);

    }

    private void deleteSelectedRow() {
        Reminder selectedReminder = table.getSelectionModel().getSelectedItem();
        if (selectedReminder != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Confirm Delete");
            confirmationAlert.setContentText("Are you sure you want to delete this record?");

            ButtonType confirmButton = new ButtonType("Delete");
            ButtonType cancelButton = new ButtonType("Cancel");

            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            confirmationAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == confirmButton) {
                    int selectedIndex = table.getSelectionModel().getSelectedIndex();
                    table.getItems().remove(selectedIndex);

                    deleteRecordFromDatabase(selectedReminder.getId());

                    showDeleteConfirmationAlert();
                }
            });
        }
    }

    private void showDeleteConfirmationAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Record deleted successfully!");
        alert.showAndWait();
    }

    private void deleteRecordFromDatabase(int recordId) {
        Database db = Database.getInstance();

        try {
            PreparedStatement statement = db.getDBConnection().prepareStatement("DELETE FROM reminders WHERE id = ?");
            statement.setInt(1, recordId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential database errors
        }
    }

    public void getAllReminders() {
        try {
            List<Reminder> reminders = Reminder.fetchRemindersFromDatabase();
            table.getItems().addAll(reminders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initTbale() {
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
    }

}
