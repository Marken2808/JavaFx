package com.school.main.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.school.main.modal.Client;
import com.school.main.modal.NetworkConnection;
import com.school.main.modal.Server;
import com.school.main.utils.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.school.main.controllers.VideoroomController.currentFrame;

public class ChatroomController implements Initializable {

    @FXML
    private JFXTextArea msgArea;

    @FXML
    private JFXTextField msgField;

    private boolean isServer=false;///////////////////////////////

    private NetworkConnection connection = isServer ? createServer() : createClient();

    private Server createServer(){
        return new Server(1998, data -> {
            Platform.runLater(() -> {
                msgArea.appendText(data.toString() + "\n");

            });
        });
    }

    private Client createClient(){
        return new Client("192.168.0.23", 1998, data -> {
            Platform.runLater(() -> {
                msgArea.appendText(data.toString() + "\n");

            });
        });
    }

    public void stop() throws IOException {
        connection.closeConnection();
    }

    @FXML
    void inputMessage(ActionEvent event) throws IOException {
        String msg = isServer ? "Server: " : "Client: ";
        msg += msgField.getText();
        msgField.clear();

        msgArea.appendText(msg + "\n");
        connection.send(msg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection.startConnection();
    }


}
