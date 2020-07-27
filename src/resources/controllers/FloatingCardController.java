package resources.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FloatingCardController implements Initializable {

    @FXML
    private ImageView quality;

    @FXML
    private StackPane n;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Image image1 = new Image(FloatingCardController.class.getResourceAsStream("./resources/images/quality/TOTS/TOTS-LEGEND.png"));
        quality.setImage(image1);

    }
}
