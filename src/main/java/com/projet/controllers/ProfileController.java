package com.projet.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {

    @FXML
    private Label roleL;

    @FXML
    private Label usernameL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the labels with the values of username and role
    }

    // Setter methods for username and role

    public void setUser(String username, String role) {
        usernameL.setText(username);
        roleL.setText(role);

    }
}
