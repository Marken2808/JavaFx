package resources.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXDrawer leftCard;

    @FXML
    private JFXDrawer centerCard;

    @FXML
    private JFXButton viewcard;

    @FXML
    private ImageView menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox menuLeft = FXMLLoader.load(getClass().getResource("../filesFXML/MenuLeft.fxml"));
            StackPane testLeftCard = FXMLLoader.load(getClass().getResource("../filesFXML/LeftCard.fxml"));
            StackPane testCenterCard = FXMLLoader.load(getClass().getResource("../filesFXML/CenterCard.fxml"));
            drawer.setSidePane(menuLeft);
            leftCard.setSidePane(testLeftCard);
            centerCard.setSidePane(testCenterCard);

            menu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

                if (drawer.isClosed() || drawer.isClosing()) {
                    drawer.open();
                    menu.setImage(new Image("./resources/images/icon/x.png"));
                } else {
                    drawer.close();
                    menu.setImage(new Image("./resources/images/icon/menu.png"));
                }
            });

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
            //e.printStackTrace();
        }

    }
}
