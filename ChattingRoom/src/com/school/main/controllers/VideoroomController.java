package com.school.main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoroomController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXMasonryPane masPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setContent(masPane);
        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
        masPane.setMinWidth(500);
    }
}
