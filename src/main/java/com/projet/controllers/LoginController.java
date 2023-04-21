package com.projet.controllers;

import java.util.Optional;

import com.projet.AppState;
import com.projet.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    void Login(ActionEvent event) {
        try {
            // Close current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // try to login the user if success show 2fa

            User currentUser = User.Login(this.username.getText(), this.password.getText());
            // boolean isSuccess = this.showDialog(currentStage, "27515642");

            if (currentUser != null) {
                this.showAlert("Login success", "Hello " + currentUser.getUsername() + " welcome back!",
                        AlertType.INFORMATION);

                // add current user to the global state
                AppState.getInstance().setLoggedIn(true);
                AppState.getInstance().setUser(currentUser);
                currentUser.setConfirmationCode();
                System.out.println(currentUser.getConfirmationCode() + " ddd");
                this.showDialog(currentUser, currentStage, "27515642");

            } else {
                this.showAlert("Login failed!", "password or username not correct", AlertType.ERROR);
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

    public boolean showDialog(User user, Stage currentStage, String tel) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome back " + user.getUsername());
        dialog.setHeaderText("Enter your confirmation code for " + user.getTel() + ":");
        dialog.setContentText("Code:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            if (name.equals("123456")) {
                currentStage.close();
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

}