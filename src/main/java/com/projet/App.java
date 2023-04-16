package com.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// import sql library
import java.sql.*;

import com.projet.utils.Utils;

/**
 * JavaFX App
 */
public class App extends Application {

    // private Connection connect = null;
    // private Statement statement = null;
    // private PreparedStatement preparedStatement = null;
    // private ResultSet resultSet = null;

    // private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // try {
        // // connect to db
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // Connection con =
        // DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "hosssem",
        // "Hossem@@@147");
        // Statement stmt = con.createStatement();
        // ResultSet rs = stmt.executeQuery("select * from Persons");
        // while (rs.next())
        // System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " +
        // rs.getString(3));
        // con.close();
        // } catch (Exception e) {
        // // TODO: handle exception
        // System.out.println(e);

        // }

        Utils.navigateTo(stage, "login");
    }

    public static void main(String[] args) {
        launch();
    }

}