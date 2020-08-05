package resources.controllers.screens;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import resources.mySQLconnection;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static resources.controllers.functions.duplicatedForms.*;

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

        Connection connection = mySQLconnection.ConnectDataBase();
        String query = "Select * from users where username = ? and password = ?";
            try{
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1,user.getText());
                pst.setString(2,pass.getText());
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Login Successfully");
                    letMake(signIn,"HomeScreen.fxml");
                }else{
                    JOptionPane.showMessageDialog(null,"Incorrect user !");
                }
            }
            catch ( Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    }

    @FXML
    void letRegister(ActionEvent e) throws IOException {
        letMake(signUp,"SignUpScreen.fxml");
    }

    @FXML
    void isEmpty(KeyEvent event) {
        Boolean isEmptyUser = isEmptyField(user,userWarning,userWarningImg);
        Boolean isEmptyPass = isEmptyField(pass,passWarning,passWarningImg);
        Boolean loadButton = isEmptyUser && isEmptyPass;
        isAllDone(loadButton,signIn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signIn.setDisable(true);

    }
}
