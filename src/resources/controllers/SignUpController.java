package resources.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import resources.mySQLconnection;

import javax.swing.*;
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
    private JFXTextField new_pass;

    @FXML
    private JFXTextField check_pass;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXSlider ageSlider;

    @FXML
    private JFXButton signUp;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}