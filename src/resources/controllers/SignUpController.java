package resources.controllers;

import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import resources.mySQLconnection;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXTextField new_user;

    @FXML
    private JFXPasswordField new_pass;

    @FXML
    private JFXPasswordField check_pass;

    @FXML
    private Label userWarning;

    @FXML
    private Label passWarning;

    @FXML
    private Label confirmWarning;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXSlider ageSlider;

    @FXML
    private JFXButton signUp;

    @FXML
    private ImageView backArrow;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        backArrow.getScene().getWindow().hide();
        Parent signInRoot = FXMLLoader.load(getClass().getResource("/resources/filesFXML/SignInScreen.fxml"));
        Stage signInStage = new Stage();
        Scene signInScene = new Scene(signInRoot);
        signInStage.setScene(signInScene);
        signInStage.show();
    }

    @FXML
    void checkingAge(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        LocalDate today = LocalDate.now();
        Period p = Period.between(date,today);
        double age = p.getYears();
        ageSlider.setValue(age);
        ageSlider.setShowTickLabels(true);
    }

//    @FXML
//    void isEmpty(MouseEvent event) {
//        RequiredFieldValidator userEmpty = new RequiredFieldValidator("Enter username !!!");
//        RequiredFieldValidator passEmpty = new RequiredFieldValidator("Enter password !!!");
//
//        new_user.getValidators().add(userEmpty);
//        new_pass.getValidators().add(passEmpty);
//
//        new_user.focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
//            if(!newValue){
//                if(new_user.validate()) {
//                    signUp.setDisable(false);
//                }
//                else {
//                    signUp.setDisable(true);
//                }
//            }
//
//        });
//
//        new_pass.focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) -> {
//            if(!newValue){
//                if(new_pass.validate()) {
//                    signUp.setDisable(false);
//                }
//                else {
//                    signUp.setDisable(true);
//                }
//
//            }
//        });
//    }

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst =null;

    @FXML
    void makeRegister(ActionEvent event) {
        connection = mySQLconnection.ConnectDataBase();
        String query = "insert into users (username,password) values (?,?)";
        try{
            pst = connection.prepareStatement(query);
            pst.setString(1,new_user.getText());
            pst.setString(2,new_pass.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Saved");
        }
        catch ( Exception e){
            JOptionPane.showMessageDialog(null,e);

        }
    }

    @FXML
    void isRight(KeyEvent event) {
        String pass = new_pass.getText();
        String check = check_pass.getText();
        if(check.equals(pass)){
            confirmWarning.setText("Matched");
        } else if(check.length()<pass.length()){
            confirmWarning.setText("Not enough");
        } else {
            confirmWarning.setText("Not match");
        }
    }

    @FXML
    void isEmpty(KeyEvent event) {
        boolean userCheck = checkingCorrection(new_user,userWarning);
        boolean passCheck = checkingCorrection(new_pass,passWarning);

        if(userCheck && passCheck){
            signUp.setDisable(false);
        }else{
            signUp.setDisable(true);
        }
    }

    public boolean checkingCorrection(Object obj, Label lb){
        int length;
        if(obj instanceof JFXTextField){
            length = new_user.getText().length();
            checkingLength(length,lb);
            if(length<3){
                return false;
            }
        } else if(obj instanceof JFXPasswordField) {
            length = new_pass.getText().length();
            checkingLength(length,lb);
            if(length<3){
                return false;
            }
        }
        return true;
    }

    public int checkingLength(int length,Label lb){
        if(length==0){
            lb.setText("Please fulfill !");
        } else if(length>0 && length<3){
            lb.setText("At lease 3 characters !");
        } else{
            lb.setText("Correct");
        }
        return length;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp.setDisable(true);
        new_user.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
            checkingCorrection(new_user, userWarning);
        });
        new_pass.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
            checkingCorrection(new_pass, passWarning);
        });
    }
}