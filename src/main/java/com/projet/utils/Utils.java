package com.projet.utils;

import com.projet.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utils {

    public static <T> T navigateTo(Stage primaryStage, String page) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("views/" + page + ".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            primaryStage.setScene(scene);
            primaryStage.show();

            return loader.getController();
        } catch (Exception e) {
            System.out.println("Page " + page + " not found");
            return null;
        }
    }
}