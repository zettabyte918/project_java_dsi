package com.projet.controllers;

import java.util.Optional;

import com.projet.AppState;
import com.projet.models.User;
import com.projet.utils.Router;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private TextField username;

    @FXML
    private TextField tel;

    @FXML
    private TextField password;

    @FXML
    void Login(ActionEvent event) {
        try {
            // current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            LoginController loginController = Router.navigateTo(currentStage, "login");
            // currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Register(ActionEvent event) {
        System.out.println("hi from register page");
    }

    public void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean showConfirmationCodeDialog(User user, Stage currentStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome back " + user.getUsername());
        dialog.setHeaderText("Enter your confirmation code for " + user.getTel() + ":");
        dialog.setContentText("Code:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String userCode = result.get();

            // get confirmation code from the user database
            String confirmation_code = user.getConfirmationCode();

            if (userCode.equals(confirmation_code)) {
                currentStage.close();
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

}