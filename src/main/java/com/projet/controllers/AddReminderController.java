package com.projet.controllers;

import com.jfoenix.controls.JFXSlider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddReminderController {
    @FXML
    private DatePicker date;

    @FXML
    private JFXSlider hour;

    @FXML
    private TextField message;

    @FXML
    private JFXSlider minute;

    @FXML
    private TextField title;

    @FXML
    void add(ActionEvent event) {
        System.out.println("hhhhhhhhhhhhhh");
    }

}
