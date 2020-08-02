package resources.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.mySQLconnection;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private AnchorPane signInPane;

    @FXML
    private ImageView userWarningImg;

    @FXML
    private ImageView passWarningImg;

    @FXML
    private Label userWarning;

    @FXML
    private Label passWarning;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton signIn;

    @FXML
    private JFXButton signUp;

    @FXML
    void makeLogin(ActionEvent event) {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pst =null;

        connection = mySQLconnection.ConnectDataBase();
        String query = "Select * from users where username = ? and password = ?";
            try{
                pst = connection.prepareStatement(query);
                pst.setString(1,user.getText());
                pst.setString(2,pass.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Username and Password is correct");
                    signIn.getScene().getWindow().hide();
                    Parent homeRoot = FXMLLoader.load(getClass().getResource("/resources/filesFXML/HomeScreen.fxml"));
                    Stage homeStage = new Stage();
                    Scene homeScene = new Scene(homeRoot);
                    homeStage.setScene(homeScene);
                    homeStage.setResizable(false);
                    homeStage.show();
                }else{
                    JOptionPane.showMessageDialog(null,"Incorrect user");
                }
            }
            catch ( Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    }

    @FXML
    void letRegister(ActionEvent e) throws IOException {
        signUp.getScene().getWindow().hide();
        Parent signUpRoot = FXMLLoader.load(getClass().getResource("/resources/filesFXML/SignUpScreen.fxml"));
        Stage signUpStage = new Stage();
        Scene signUpScene = new Scene(signUpRoot);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    @FXML
    void isEmpty(KeyEvent event) {
        boolean userCheck = checkingCorrection(user,userWarning);
        //System.out.println(userCheck);
        boolean passCheck = checkingCorrection(pass,passWarning);
        //System.out.println(passCheck);

        if(userCheck && passCheck){
            signIn.setDisable(false);
        }else{
            signIn.setDisable(true);
        }
    }

    public boolean checkingCorrection(Object obj, Label lb){
        int length;
        if(obj instanceof JFXTextField){
            length = user.getText().length();
            return checkingLength(length,lb);
        } else if(obj instanceof JFXPasswordField) {
            length = pass.getText().length();
            return checkingLength(length,lb);
        }
        return false;
    }

    public boolean checkingLength(int length,Label lb){
        if(length==0){
            lb.setText("Please fulfill");
        } else if(length>0 && length<3){
            lb.setText("At lease 3 characters");
        } else{
            lb.setText("Correct");
            return true;
        }
        return false;
    }

    public void warningImage(boolean isCorrect, ImageView img, Label lb){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setNode(lb);
        fade.setCycleCount(1);


        lb.getStyleClass().clear();
        img.setVisible(true);
        lb.setVisible(true);
        if(!isCorrect){

            img.setImage(new Image("./resources/images/icon/alert-circle.png"));
            lb.getStyleClass().add("failed");
        }else {

            lb.getStyleClass().add("succeed");
            img.setImage(new Image("./resources/images/icon/check-circle.png"));

            fade.play();
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signIn.setDisable(true);
        user.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
            boolean isCorrect = checkingCorrection(user, userWarning);
            warningImage(isCorrect,userWarningImg,userWarning);
        });

        pass.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
            boolean isCorrect = checkingCorrection(pass, passWarning);
            warningImage(isCorrect,passWarningImg,passWarning);
        });
    }
}
