package screens.SignInScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton signIn;

    @FXML
    private JFXButton signUp;

    @FXML
    void makeLogin(ActionEvent e) {
        String username =  user.getText();
        String password = pass.getText();
        //System.out.println(username + password);
        if ( username.equals("zxc")&&password.equals("qwe")){
            System.out.println("Correct");
        }
        else{
            System.out.println("Wrong");
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
