package com.school.main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXMasonryPane;
import com.school.main.utils.SplitPaneDividerSlider;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;


public class ClassroomController implements Initializable
{
    @FXML
    private SplitPane centerSplitPane;

    @FXML
    private ToggleButton leftToggleButton;

    @FXML
    private AnchorPane viewStack;

    @FXML
    private JFXDrawersStack viewDraw;

    @FXML
    private JFXDrawersStack chatDraw;

    @FXML
    private AnchorPane chatStack;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        final SplitPaneDividerSlider leftSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 0, SplitPaneDividerSlider.Direction.LEFT);
//        final SplitPaneDividerSlider rightSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 1, SplitPaneDividerSlider.Direction.RIGHT);

        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                leftSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

//        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
//            {
//                rightSplitPaneDividerSlider.setAimContentVisible(t1);
//            }
//        });

        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {

            }
        });

//        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
//        {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
//            {
//
//            }
//        });

        try {
            AnchorPane chatRoom = FXMLLoader.load(getClass().getResource("../../resources/Chatroom.fxml"));
            chatDraw.setContent(chatRoom);
            ScrollPane videoRoom = FXMLLoader.load(getClass().getResource("../../resources/Videoroom.fxml"));
            viewDraw.setContent(videoRoom);


//            SplitPaneDividerSlider

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}