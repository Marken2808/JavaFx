package resources.controllers.tabs;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import resources.controllers.functions.Users;
import resources.mySQLconnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserTabController implements Initializable {

    @FXML
    private TableView<Users> tableUser;

    @FXML
    private TableColumn<Users, String> roleCol;

    @FXML
    private TableColumn<Users, String> userCol;

    @FXML
    private TableColumn<Users, String> passCol;

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXTextField passField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private ChoiceBox<String> roleBox;

    ObservableList<Users> userLists = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Connection conn = mySQLconnection.ConnectDataBase();
            ResultSet rs = conn.createStatement().executeQuery("Select * from users");
            while (rs.next()){
                userLists.add(new Users(
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("password")

                ));
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }

        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableUser.setItems(userLists);
    }
}
