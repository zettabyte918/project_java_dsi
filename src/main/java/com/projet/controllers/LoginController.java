package com.projet.controllers;

import java.util.Optional;

import com.projet.AppState;
import com.projet.models.User;
import com.projet.utils.Router;
import com.projet.utils.OrangeSMS.OrangeSMS;
import com.projet.worker.Worker;

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

    // for register
    @FXML
    private TextField tel;

    @FXML
    void Login(ActionEvent event) {
        try {
            // current login window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // try to login the user if success show 2fa
            User currentUser = User.Login(this.username.getText(), this.password.getText());

            if (currentUser != null) {
                this.showAlert("Login success", "Hello " + currentUser.getUsername() + " welcome back!",
                        AlertType.INFORMATION);

                // add current user to the global state
                AppState.getInstance().setLoggedIn(true);
                AppState.getInstance().setUser(currentUser);
                currentUser.setConfirmationCode();

                // send 2fa code
                OrangeSMS.send2FaSMS(AppState.getInstance().getUser().getTel(),
                        "Bonjour, " + currentUser.getConfirmationCode()
                                + " est votre code de confirmation sur reminders app");

                System.out.println(currentUser.getConfirmationCode() + " confirmation code");
                if (this.showConfirmationCodeDialog(currentUser, currentStage)) {
                    System.out.println("correct 2fa");
                    // start worker
                    Worker worker = new Worker();
                    worker.startCheckingReminders();
                } else {
                    System.out.println("incorrect 2fa");
                }

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

    @FXML
    void Register(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Router.navigateTo(currentStage, "register", false);
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
                Router.navigateTo(currentStage, "reminders", false);
                return true;
            } else {
                return false;
            }

        }

        return false;
    }

}