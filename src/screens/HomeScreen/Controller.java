package screens.HomeScreen;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger menu;

    @FXML
    private ImageView menu1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox menuLeft = FXMLLoader.load(getClass().getResource("../../drawers/MenuLeft.fxml"));
            drawer.setSidePane(menuLeft);
            menu1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

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
