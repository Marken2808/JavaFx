package com.school.main.controllers;

import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.TranslateTransition;
import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassroomController implements Initializable {

    @FXML
    private SplitPane splitPane;

    @FXML
    private JFXDrawersStack viewStack;

    @FXML
    private JFXDrawersStack chatStack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AnchorPane chatRoom = FXMLLoader.load(getClass().getResource("../../resources/Chatroom.fxml"));
            chatStack.setContent(chatRoom);
            ScrollPane videoRoom = FXMLLoader.load(getClass().getResource("../../resources/Videoroom.fxml"));
            viewStack.setContent(videoRoom);

//            SplitPaneDividerSlider

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
