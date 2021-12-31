package resources.controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CenterCardController implements Initializable {

    @FXML
    private StackPane centerCard;

    @FXML
    private Group model;

    @FXML
    private ParallelCamera perspectiveCamera;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
