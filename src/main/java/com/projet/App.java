package com.projet;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import com.projet.utils.Database;
import com.projet.utils.TestDBResponse;
import com.projet.worker.Worker;
import com.projet.utils.Router;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // start background worker
        // this.startWorkers(stage);

        // test database if ok otherwise show alert
        TestDBResponse res = Database.getInstance().TestConnection();

        if (res.getStatus() == 0) {
            this.showAlert(res.getMessage());
            Database.getInstance().getDBConnection().close();

        } else {
            Router.navigateTo(stage, "login");
        }

    }

    public static void main(String[] args) {
        launch();
    }

    public void startWorkers(Stage stage) {
        Worker worker = new Worker();
        worker.startCheckingReminders();

        // stage.setOnCloseRequest(e -> {
        // worker.stopCheckingReminders();
        // });

    }

    public void showAlert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).show();
    }

}