package com.projet.controllers;

import java.util.Optional;

import com.projet.utils.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btn_login;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    void Login(ActionEvent event) {
        try {
            // Close current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load profile.fxml and create new window
            // FXMLLoader loader = new
            // FXMLLoader(getClass().getResource("/application/views/profile.fxml"));
            // Parent root = loader.load();
            boolean isSuccess = this.showDialog(currentStage, "27515642");

            if (isSuccess) {
                ProfileController profileController = Router.navigateTo(currentStage, "profile");

                // Set the values of username and role in the ProfileController
                profileController.setUser("john", "Adminn"); // Example username value
            }

            // show dialog

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalide code provided", ButtonType.OK,
                ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("yes");

        } else {
            System.out.println("no");
        }

    }

    public boolean showDialog(Stage currentStage, String tel) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText("Enter your confirmation code for " + tel + ":");
        dialog.setContentText("Code:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            if (name.equals("123456")) {
                currentStage.close();
                return true;
            } else {
                this.showAlert();
                return false;
            }

        }

        return false;
    }

}