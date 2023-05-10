package com.projet.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RemindersController {
    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> table_date;

    @FXML
    private TableColumn<?, ?> table_id;

    @FXML
    private TableColumn<?, ?> table_message;

    @FXML
    private TableColumn<?, ?> table_title;
}
