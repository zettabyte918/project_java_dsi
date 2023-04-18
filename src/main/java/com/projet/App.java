package com.projet;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;

import com.projet.utils.Db;
import com.projet.utils.TestDBResponse;
import com.projet.utils.Router;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // test database if ok otherwise show alert

        TestDBResponse res = new Db().TestConnection();

        if (res.getStatus() == 0) {
            this.showAlert(res.getMessage());
        } else {
            Router.navigateTo(stage, "login");
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void showAlert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).show();
    }

}