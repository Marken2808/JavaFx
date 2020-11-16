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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane viewStack;

    @FXML
    private JFXDrawersStack viewDraw;

    @FXML
    private JFXDrawersStack chatDraw;

    @FXML
    private AnchorPane chatStack;

    @FXML
    public Button startBtn;

    // a timer for acquiring the video stream
    public ScheduledExecutorService timer;
    // the OpenCV object that realizes the video capture
    public VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    public boolean cameraActive;
    // the id of the camera to be used
    public static int cameraId = 0;

    public static ImageView currentFrame;   //point this frame to anther frame
//    public static JFXButton btnImg = new JFXButton();
    public void startCamera()
    {
        if (!this.cameraActive)
        {
            // start the video capture
            this.capture.open(this.cameraId);

            // is the video stream available?
            if (this.capture.isOpened())
            {
                this.cameraActive = true;
                Runnable frameGrabber = new Runnable() {
                    @Override
                    public void run()
                    {
                        // effectively grab and process a single frame
                        Mat frame = grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frame);
                        updateImageView(currentFrame, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.startBtn.setText("Stop Camera");
            }

            else
            {
                // log the error
                System.err.println("Impossible to open the camera connection...");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content
            this.startBtn.setText("Start Camera");
//            btnImg.setGraphic(new ImageView(new Image("../resources/images/test/111.jpg")));
            // stop the timer
            this.stopAcquisition();
        }

    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Mat} to show
     */
    public Mat grabFrame()
    {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGRA2BGR);
                }

            }
            catch (Exception e)
            {
                // log the error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    public void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    public void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    public void setClosed()
    {
        this.stopAcquisition();
    }



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