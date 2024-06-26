package com.projet.utils;

import com.projet.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {

    public static <T> T navigateTo(Stage primaryStage, String page, Boolean newPage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + page + ".fxml"));
            Parent root = loader.load();
            Scene scene;
            if (page == "reminders") {
                scene = new Scene(root, 800, 400);
            } else {
                scene = new Scene(root, 600, 400);

            }

            if (newPage) {
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.show();
            } else {
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            }

            return loader.getController();
        } catch (Exception e) {
            return null;
        }

    }
}