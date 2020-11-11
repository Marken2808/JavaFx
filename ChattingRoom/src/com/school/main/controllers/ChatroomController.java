package com.school.main.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.school.main.Client;
import com.school.main.NetworkConnection;
import com.school.main.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatroomController implements Initializable {

    @FXML
    private JFXTextArea msgArea;

    @FXML
    private JFXTextField msgField;

    private boolean isServer=false;///////////////////////////////

    private NetworkConnection connection = isServer ? createServer() : createClient();


    private Server createServer(){
        return new Server(2808, data -> {
            Platform.runLater(() -> {
                msgArea.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient(){
        return new Client("127.0.0.1", 2808, data -> {
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
