package resources.controllers.screens;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXDrawer leftCard;

    @FXML
    private JFXDrawer centerCard;

    @FXML
    private JFXButton viewcard;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            StackPane testLeftCard = FXMLLoader.load(getClass().getResource("../../filesFXML/LeftCard.fxml"));
            StackPane testCenterCard = FXMLLoader.load(getClass().getResource("../../filesFXML/CenterCard.fxml"));

            viewcard.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

                if (centerCard.isClosed() || centerCard.isClosing()) {
                    centerCard.open();
//                    leftCard.open();
                } else {
                    centerCard.close();
//                    leftCard.close();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
