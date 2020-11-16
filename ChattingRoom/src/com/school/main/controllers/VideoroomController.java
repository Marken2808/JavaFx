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

public class VideoroomController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public static JFXMasonryPane masPane = new JFXMasonryPane();

    // a timer for acquiring the video stream
    public ScheduledExecutorService timer;
    // the OpenCV object that realizes the video capture
    public VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    public boolean cameraActive = false;
    // the id of the camera to be used
    public static int cameraId = 0;


    private static int slot = 15;
    public static ImageView[] currentFrame = new ImageView[slot];   //point this frame to anther frame
    public static JFXButton[] btnImgEx = new JFXButton[slot];

    public static void initFrame(){

        for (int i = 0; i < btnImgEx.length; i++) {

            currentFrame[i] = new ImageView();
            currentFrame[i].setFitHeight(150);
            currentFrame[i].setFitWidth(200);
//            System.out.println(currentFrame[i].getImage());

            btnImgEx[i] = new JFXButton();
            masPane.getChildren().addAll(btnImgEx[i]);
            btnImgEx[i].setGraphic(currentFrame[i]);
            btnImgEx[i].setText("Hello");
            btnImgEx[i].setFont(Font.font(16));
            btnImgEx[i].setContentDisplay(ContentDisplay.TOP);
            btnImgEx[i].setGraphicTextGap(10);
            btnImgEx[i].setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        }
    }

    public void startFrame() {
        this.cameraActive = true;
        Runnable frameGrabber = new Runnable() {
            @Override
            public void run() {
//              System.out.println("ok");
                Mat frame = grabFrame();    // effectively grab and process a single frame
                Image imageToShow = Utils.mat2Image(frame); // convert and show the frame
                for (int i = 0; i < btnImgEx.length; i++) {
                    updateImageView(currentFrame[i], imageToShow);

                }
            }
        };

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        initFrame();    // init

        scrollPane.setContent(masPane);
        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
        masPane.setMinWidth(500);

    }
}
