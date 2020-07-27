package resources.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import resources.mySQLconnection;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

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

                    }else{
                        JOptionPane.showMessageDialog(null,"Incorrect user");

                    }
                }
                catch ( Exception e){
                    JOptionPane.showMessageDialog(null,e);

                }
    }

//    void makeLogin(ActionEvent e) {
//        String username =  user.getText();
//        String password = pass.getText();
//        //System.out.println(username + password);
//        if ( username.equals("zxc")&&password.equals("qwe")){
//            System.out.println("Correct");
//        }
//        else{
//            System.out.println("Wrong");
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        user.getValidators().add(validator);
        validator.setMessage("No input given!");
        user.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    user.validate();
                }
            }
        });
    }
}
