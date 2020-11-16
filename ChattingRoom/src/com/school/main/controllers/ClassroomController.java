package com.school.main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXMasonryPane;
import com.school.main.utils.SplitPaneDividerSlider;
import com.school.main.utils.Utils;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


public class ClassroomController implements Initializable
{
    @FXML
    private SplitPane centerSplitPane;

    @FXML
    private ToggleButton leftToggleButton;

    @FXML
    private ToggleButton rightToggleButton;

    @FXML
    private AnchorPane viewStack;

    @FXML
    private JFXDrawersStack viewDraw;

    @FXML
    private AnchorPane chatStack;

    @FXML
    private JFXDrawersStack chatDraw;

    @FXML
    private AnchorPane docuStack;

    @FXML
    private JFXDrawersStack docuDraw;

    @FXML
    public Button startBtn;

    VideoroomController vidr = new VideoroomController();

    @FXML
    void startCamera(ActionEvent event) {

        if (!vidr.cameraActive) {
            vidr.capture.open(vidr.cameraId); // start the video capture
            if (vidr.capture.isOpened()) {  // is the video stream available?
                vidr.startFrame();

                startBtn.setText("Stop Camera");    // update the button content
            } else {
                System.err.println("Impossible to open the camera connection...");  // log the error
            }
        } else {
            vidr.cameraActive = false;  // the camera is not active at this point
            startBtn.setText("Start Camera");   // update again the button content
            vidr.stopAcquisition();     // stop the timer
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        final SplitPaneDividerSlider leftSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 1, SplitPaneDividerSlider.Direction.LEFT);
        final SplitPaneDividerSlider rightSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 0, SplitPaneDividerSlider.Direction.RIGHT);

        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                leftSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                rightSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

//        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
//
//            }
//        });
//
//        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
//
//            }
//        });

        try {
            AnchorPane chatRoom = FXMLLoader.load(getClass().getResource("../../resources/Chatroom.fxml"));
            chatDraw.setContent(chatRoom);
            ScrollPane videoRoom = FXMLLoader.load(getClass().getResource("../../resources/Videoroom.fxml"));
            viewDraw.setContent(videoRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }


        vidr.initFrame();
    }

}