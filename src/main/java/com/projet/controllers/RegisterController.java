package com.projet.controllers;

import java.util.Optional;

import com.projet.models.User;
import com.projet.utils.Router;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
            Router.navigateTo(currentStage, "login");
            // currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Register(ActionEvent event) {
        try {
            // current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // try to login the user if success show 2fa
            boolean currentUser = User.Register(this.username.getText(), this.password.getText(), this.tel.getText());

            if (currentUser) {
                this.showAlert("Register success", "Hello " + this.username.getText() + " welcome !",
                        AlertType.INFORMATION);

                // redirect to login page:
                Router.navigateTo(currentStage, "login");

            } else {
                this.showAlert("Register failed!", "User already exist!", AlertType.ERROR);
            }

            // ProfileController profileController = Router.navigateTo(currentStage,
            // "profile");
            // // Set the values of username and role in the ProfileController
            // profileController.setUser("john", "Adminn"); // Example username value

        } catch (Exception e) {
            e.printStackTrace();
        }
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