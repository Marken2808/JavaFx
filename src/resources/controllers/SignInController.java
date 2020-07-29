package resources.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private Label userLength;

    @FXML
    private Label passLength;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton signIn;

    @FXML
    private JFXButton signUp;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst =null;

    @FXML
    void makeLogin(ActionEvent event) {
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
    void isEmpty(MouseEvent event) {
        RequiredFieldValidator userEmpty = new RequiredFieldValidator("Enter username ⚠");
        RequiredFieldValidator passEmpty = new RequiredFieldValidator("Enter password ⚠");

        user.getValidators().add(userEmpty);
        pass.getValidators().add(passEmpty);

        int length = user.getText().length();
        System.out.println("real"+length);
        if(length<3&&length>0){
            userLength.setVisible(true);
        }else{
            userLength.setVisible(false);
        }

        user.focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
            if(!newValue){
                if(user.validate()) {
                    signIn.setDisable(false);
                }
                else {
                    signIn.setDisable(true);
                }
            }
        });

        pass.focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
            if(!newValue){
                if(pass.validate()) {
                    signIn.setDisable(false);
                }
                else {
                    signIn.setDisable(true);
                }

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
