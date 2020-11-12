package com.school.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/Classroom.fxml"));
        Parent root = loader.load();
        //root.setStyle("-fx-background-color: whitesmoke;");
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Chatroom");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
