package resources.controllers.drawers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import resources.controllers.functions.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static resources.controllers.functions.duplicatedForms.*;

public class MenuLeftController implements Initializable {

    @FXML
    private VBox drawerPane;

    @FXML
    private ImageView avatar;

    @FXML
    private Label typeUser;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton isHome;


    @FXML
    void isHomeClicked(MouseEvent event) {
        //System.out.println("Home click");
    }

    @FXML
    private JFXButton signOut;

    @FXML
    void makeLogOut(MouseEvent event) throws IOException {
        letMake(signOut, "SignInScreen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Users.checkProfileName(typeUser);
        if (Users.checkRole().equals("Admin")) {
            dashboard.setVisible(true);
        } else {
            dashboard.setVisible(false);
        }
    }
}
