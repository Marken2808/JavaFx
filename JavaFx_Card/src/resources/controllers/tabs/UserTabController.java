package resources.controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import resources.controllers.functions.Users;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static resources.mySQLconnection.*;

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
    private TableColumn<Users, String> nameCol;

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXTextField passField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton delBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    void deleteDB(MouseEvent event) {

        String query = "Delete from users where username = ?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, userField.getText());
            pst.execute();
            getUpdateTable();
        } catch (Exception e) {
        }

    }

    @FXML
    void insertDB(MouseEvent event) {
        String query = "Insert into users (role,username,password,profilename) values(?,?,?,?)";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1,roleBox.getValue());
            pst.setString(2,userField.getText());
            pst.setString(3,passField.getText());
            pst.setString(4,nameField.getText());
            pst.execute();
            getUpdateTable();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }

    @FXML
    void updateDB(MouseEvent event) {
        try {
            String newRole = roleBox.getValue();
            String newUser = userField.getText();
            String newPass = passField.getText();
            String newName = nameField.getText();
            String sql = "Update users Set " +
                    "role = '"+newRole+"'," +
                    "username= '"+newUser+"'," +
                    "password= '"+newPass+"'," +
                    "profilename= '"+newName+"' " +
                    "where username='"+newUser+"' ";
            pst= connection.prepareStatement(sql);
            pst.execute();
            getUpdateTable();
        } catch (Exception e) {
        }

    }

    @FXML
    void clickOnTable(MouseEvent event) {
        delBtn.setDisable(true);
        editBtn.setDisable(true);
        addBtn.setDisable(false);

        Users selectedUser = tableUser.getSelectionModel().getSelectedItem();

        if(!tableUser.getSelectionModel().isEmpty()){
            if(event.getClickCount() == 2){
                getDataOnRow(selectedUser);
                delBtn.setDisable(false);
                editBtn.setDisable(false);
                addBtn.setDisable(true);
            }else{
                tableUser.getSelectionModel().clearSelection(tableUser.getSelectionModel().getSelectedIndex());
            }
        }
    }

    void getDataOnRow(Users us){
        roleBox.setValue(us.getRole());
        userField.setText(us.getUsername());
        passField.setText(us.getPassword());
        nameField.setText(us.getProfilename());
    }



    void getUpdateTable(){
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("profilename"));

        roleBox.setItems(roleList);

        userLists = getUserDataOnTable();
        tableUser.setItems(userLists);

    }

    ObservableList<Users> userLists = FXCollections.observableArrayList();
    ObservableList<String> roleList = FXCollections.observableArrayList("Admin","Editor","Member");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getUpdateTable();

    }


}
