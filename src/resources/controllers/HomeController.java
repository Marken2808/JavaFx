package resources.controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private ImageView menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox menuLeft = FXMLLoader.load(getClass().getResource("../FilesFXML/MenuLeft.fxml"));
            //StackPane card = FXMLLoader.load(getClass().getResource("../FilesFXML/FloatingCard.fxml"));
            drawer.setSidePane(menuLeft);
            menu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

                if (drawer.isClosed() || drawer.isClosing()) {
                    drawer.open();
                } else {
                    drawer.close();
                }
            });
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
