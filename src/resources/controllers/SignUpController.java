package resources.controllers;

import com.jfoenix.controls.*;
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

import static resources.functions.duplicated.*;

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
    private ImageView userWarningImg;

    @FXML
    private ImageView passWarningImg;

    @FXML
    private ImageView confirmWarningImg;

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
    void isEmpty(KeyEvent event) {
        Boolean isEmptyUser = isEmptyField(new_user,userWarning,userWarningImg);
        Boolean isEmptyPass = isEmptyField(new_pass,passWarning,passWarningImg);
        Boolean isConfirmPass = isConfirmRight(new_pass,check_pass,confirmWarning,confirmWarningImg);

        //System.out.println("user "+isEmptyUser + " pass "+isEmptyPass + " conf "+isConfirmPass);
        Boolean loadButton = isEmptyUser && isEmptyPass && isConfirmPass;
        isAllDone(loadButton,signUp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp.setDisable(true);
    }
}