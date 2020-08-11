package resources.controllers.screens;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import resources.controllers.functions.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label title;

    @FXML
    private Label typeUser;

    @FXML
    private Label typePass;

    @FXML
    private ImageView menu;

    @FXML
    private HBox headerPane;

    @FXML
    private StackPane bodyPane;

    @FXML
    public JFXDrawer drawerPane;

    @FXML
    void isMenuClicked(MouseEvent event) throws IOException {
        // set Drawer always starts from left 0.0;
        AnchorPane.setTopAnchor(drawerPane,40.0);
        AnchorPane.setLeftAnchor(drawerPane, 0.0);
        AnchorPane.setBottomAnchor(drawerPane,0.0);

        VBox menuLeft = FXMLLoader.load(getClass().getResource("/resources/filesFXML/MenuLeft.fxml"));
        drawerPane.setSidePane(menuLeft);
        showElements(menuLeft);

        if (drawerPane.isClosed() || drawerPane.isClosing()) {
            drawerPane.open();
            menu.setImage(new Image("./resources/images/icon/x.png"));
        } else {
            drawerPane.close();
            menu.setImage(new Image("./resources/images/icon/menu.png"));
        }
    }

    @FXML
    void drawerExit(MouseEvent event) {

        if(drawerPane.isOpened() || drawerPane.isOpening()) {
            drawerPane.close();
            menu.setImage(new Image("./resources/images/icon/menu.png"));
        }
        // set Drawer always hide away from main scene;
        AnchorPane.clearConstraints(drawerPane);
        AnchorPane.setLeftAnchor(drawerPane, -200.0);
    }



    public void showElements(VBox menuLeft) throws IOException {
        AnchorPane home = FXMLLoader.load(getClass().getResource("../../filesFXML/HomeScreen.fxml"));
        AnchorPane setting = FXMLLoader.load(getClass().getResource("../../filesFXML/SettingScreen.fxml"));
        AnchorPane dashboard = FXMLLoader.load(getClass().getResource("../../filesFXML/ActivityScreen.fxml"));

        for (Node node : menuLeft.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Home":
                            bodyPane.getChildren().setAll(home);
                            title.setText("Home");
                            break;
                        case "Setting":
                            bodyPane.getChildren().setAll(setting);
                            title.setText("Setting");
                            break;
                        case "Dashboard":
                            bodyPane.getChildren().setAll(dashboard);
                            title.setText("Dashboard");
                            break;
                    }
                });
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Users.setUsername(typeUser);
        Users.setPassword(typePass);


    }

}