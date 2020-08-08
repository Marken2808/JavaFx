package resources.controllers.screens;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private JFXToggleButton toggleButton;

    @FXML
    void autoFill(MouseEvent event) {
        fastTrack(user,userWarning,userWarningImg);
        fastTrack(pass,passWarning,passWarningImg);
        signIn.setDisable(false);
    }


    private static SignInController instance;
    public SignInController(){
        instance = this;
    }
    public static SignInController getInstance(){
        if(instance == null){
            instance = new SignInController();
        }
        return instance;
    }
    public String username(){
        return user.getText();
    }
    public String password(){
        return pass.getText();
    }

    public boolean role(){
        if(user.getText().equals("admin")){
            return true;
        }else {
            return false;
        }
    }

    public void createConnection() throws IOException {

        String userDB = user.getText() ;
        String passDB = pass.getText() ;
        Connection connection = mySQLconnection.ConnectDataBase();
        String query = "Select * from users where username = ? and password = ?";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1,userDB);
            pst.setString(2,passDB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Login Successfully");
                letMake(signIn,"PrimaryScreen.fxml");

            }else{
                JOptionPane.showMessageDialog(null,"Incorrect user !");
            }
        }
        catch ( Exception e){
            //JOptionPane.showMessageDialog(null,e);
        }
    }


    @FXML
    void makeLogin(ActionEvent event) throws IOException {
        createConnection();
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