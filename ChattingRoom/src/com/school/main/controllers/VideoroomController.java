package com.school.main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.school.main.utils.Utils;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VideoroomController extends ClassroomController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXMasonryPane masPane;


    public JFXButton[] btnImgEx = new JFXButton[5];

    public void embedFrame(){

        currentFrame = new ImageView();
        currentFrame.setFitHeight(150);
        currentFrame.setFitWidth(200);
        System.out.println(currentFrame.getImage());
        for (int i = 0; i < btnImgEx.length; i++) {
            btnImgEx[i] = new JFXButton();
            masPane.getChildren().addAll(btnImgEx[i]);
            btnImgEx[i].setGraphic(currentFrame);
            btnImgEx[i].setText("Hello");
            btnImgEx[i].setFont(Font.font(16));
            btnImgEx[i].setContentDisplay(ContentDisplay.TOP);
            btnImgEx[i].setGraphicTextGap(10);
            btnImgEx[i].setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setContent(masPane);
        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
        masPane.setMinWidth(500);

        embedFrame();
    }
}
