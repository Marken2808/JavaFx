package resources.controllers.drawers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static resources.controllers.functions.duplicatedForms.*;

public class MenuLeftController {

    @FXML
    private ImageView avatar;

    @FXML
    private JFXButton user;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton signOut;

    @FXML
    void makeLogOut(MouseEvent event) throws IOException {
        letMake(signOut,"SignInScreen.fxml");
    }

    @FXML
    void isShown(ActionEvent event) {

    }

    @FXML
    void setUser(ActionEvent event) {

    }

}
