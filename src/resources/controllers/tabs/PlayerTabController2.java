package resources.controllers.tabs;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import resources.controllers.functions.Players;

import java.net.URL;
import java.util.ResourceBundle;

import static resources.mySQLconnection.getPlayerDataOnTable;

public class PlayerTabController2 implements Initializable {

    @FXML
    private TreeTableView<Players> treePlayer;

    @FXML
    private TreeTableColumn<Players, String> nameCol;

    @FXML
    private TreeTableColumn<Players, String> clubCol;

    @FXML
    private TreeTableColumn<Players, String> positionCol;

    @FXML
    private TreeTableColumn<Players, Integer> paceCol;

    @FXML
    private TreeTableColumn<Players, Integer> shootingCol;

    @FXML
    private TreeTableColumn<Players, Integer> passingCol;

    @FXML
    private TreeTableColumn<Players, Integer> agilityCol;

    @FXML
    private TreeTableColumn<Players, Integer> defendingCol;

    @FXML
    private TreeTableColumn<Players, Integer> physicalCol;

    @FXML
    void clickOnTree(MouseEvent event) {

    }

    ObservableList<Players>  playerLists = FXCollections.observableArrayList();
    void getUpdateTable(){
        nameCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,String>("pname")
        );
        clubCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,String>("pclub")
        );
        positionCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,String>("position")
        );
        paceCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statPace")
        );
        shootingCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statShooting")
        );
        passingCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statPassing")
        );
        agilityCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statAgility")
        );
        defendingCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statDefending")
        );
        physicalCol.setCellValueFactory(
                new TreeItemPropertyValueFactory<Players,Integer>("statPhysical")
        );

        playerLists = getPlayerDataOnTable();
        TreeItem<Players> root = new RecursiveTreeItem<>(playerLists, RecursiveTreeObject::getChildren);
        treePlayer.setRoot(root);
        treePlayer.setShowRoot(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getUpdateTable();
    }
}
