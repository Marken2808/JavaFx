package resources.controllers.screens;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView menu;

    @FXML
    private StackPane bodyPane;

    @FXML
    public JFXDrawer drawerPane;


    public void drawerChecking(Object cls){
        if(drawerPane.isOpened() || drawerPane.isOpening()) {
            menu.setImage(new Image("./resources/images/icon/menu.png"));
            drawerPane.close();
        }
        else if(cls instanceof ImageView){
            if (drawerPane.isClosed() || drawerPane.isClosing()) {
                drawerPane.open();
                menu.setImage(new Image("./resources/images/icon/x.png"));
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            VBox menuLeft = FXMLLoader.load(getClass().getResource("../../filesFXML/MenuLeft.fxml"));
            AnchorPane body = FXMLLoader.load(getClass().getResource("../../filesFXML/HomeComp.fxml"));
            drawerPane.setSidePane(menuLeft);
            bodyPane.getChildren().add(body);

            menu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                drawerChecking(menu);
            });

            bodyPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                drawerChecking(bodyPane);
            });


        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
