package resources.controllers.tabs;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import resources.controllers.functions.Players;

import java.net.URL;
import java.util.ResourceBundle;

import static resources.mySQLconnection.getPlayerDataOnTable;

public class PlayerTabController2 implements Initializable {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField clubField;

    @FXML
    private JFXTextField posField;

    @FXML
    private TextField accelerationBox;
    @FXML
    private JFXSlider accelerationSlider;

    @FXML
    private TextField sprintSpeedBox;
    @FXML
    private JFXSlider sprintSpeedSlider;

    @FXML
    private TextField jumpingBox;
    @FXML
    private JFXSlider jumpingSlider;

    @FXML
    private TextField strengthBox;
    @FXML
    private JFXSlider strengthSlider;

    @FXML
    private TextField aggressionBox;
    @FXML
    private JFXSlider aggressionSlider;

    @FXML
    private TextField positioningBox;
    @FXML
    private JFXSlider positioningSlider;

    @FXML
    private TextField finishingBox;
    @FXML
    private JFXSlider finishingSlider;

    @FXML
    private TextField shotPowerBox;
    @FXML
    private JFXSlider shotPowerSlider;

    @FXML
    private TextField longShotBox;
    @FXML
    private JFXSlider longShotSlider;

    @FXML
    private TextField volleysBox;
    @FXML
    private JFXSlider volleysSlider;

    @FXML
    private TextField penaltiesBox;
    @FXML
    private JFXSlider penaltiesSlider;

    @FXML
    private TextField visionBox;
    @FXML
    private JFXSlider visionSlider;

    @FXML
    private TextField crossingBox;
    @FXML
    private JFXSlider crossingSlider;

    @FXML
    private TextField freeKickBox;
    @FXML
    private JFXSlider freeKickSlider;

    @FXML
    private TextField shortPassingBox;
    @FXML
    private JFXSlider shortPassingSlider;

    @FXML
    private TextField longPassingBox;
    @FXML
    private JFXSlider longPassingSlider;

    @FXML
    private TextField curveBox;
    @FXML
    private JFXSlider curveSlider;

    @FXML
    private TextField agilityBox;
    @FXML
    private JFXSlider agilitySlider;

    @FXML
    private TextField balanceBox;
    @FXML
    private JFXSlider balanceSlider;

    @FXML
    private TextField reactionsBox;
    @FXML
    private JFXSlider reactionsSlider;

    @FXML
    private TextField ballControlBox;
    @FXML
    private JFXSlider ballControlSlider;

    @FXML
    private TextField dribblingBox;
    @FXML
    private JFXSlider dribblingSlider;

    @FXML
    private TextField interceptionsBox;
    @FXML
    private JFXSlider interceptionsSlider;

    @FXML
    private TextField headingBox;
    @FXML
    private JFXSlider headingSlider;

    @FXML
    private TextField markingBox;
    @FXML
    private JFXSlider markingSlider;

    @FXML
    private TextField standTackleBox;
    @FXML
    private JFXSlider standTackleSlider;

    @FXML
    private TextField slidingTackleBox;
    @FXML
    private JFXSlider slidingTackleSlider;

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
        
        if(event.getClickCount() == 2){
            Players selectedPlayer = treePlayer.getSelectionModel().getSelectedItem().getValue();
            getDataOnRow(selectedPlayer);
//            delBtn.setDisable(false);
//            editBtn.setDisable(false);
//            addBtn.setDisable(true);
        }else{
            treePlayer.getSelectionModel().clearSelection(treePlayer.getSelectionModel().getSelectedIndex());
        }


    }

    void setDataShow(TextField statField, JFXSlider statSlider, int statNum){
        statSlider.setValue(statNum);
        statField.setText(String.valueOf(statNum));

        statSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
              statField.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        );


        statField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                statField.setText(newValue.replaceAll("[^\\d]", ""));
            } else if(newValue.isEmpty()){
                statField.setStyle(  "-fx-text-box-border: #ff1a1a ; -fx-focus-color: #ff1a1a ;");
            }
            else {
                statField.setStyle(null);
                statSlider.valueProperty().setValue( Double.parseDouble(newValue));
            }
        });
    }
    
    void getDataOnRow(Players pl){
        nameField.setText(pl.getPname());
        clubField.setText(pl.getPclub());
        posField.setText(pl.getPosition());

        setDataShow(accelerationBox,accelerationSlider,pl.getAcceleration());
        setDataShow(sprintSpeedBox,sprintSpeedSlider,pl.getSprintspeed());
        setDataShow(jumpingBox, jumpingSlider,pl.getJumping());
        setDataShow(strengthBox, strengthSlider,pl.getStrength());
        setDataShow(aggressionBox, aggressionSlider,pl.getAggression());
        setDataShow(positioningBox, positioningSlider,pl.getPositioning());
        setDataShow(finishingBox, finishingSlider,pl.getFinishing());
        setDataShow(shotPowerBox, shotPowerSlider,pl.getShotpower());
        setDataShow(longShotBox, longShotSlider,pl.getLongshot());
        setDataShow(volleysBox, volleysSlider,pl.getVolleys());
        setDataShow(penaltiesBox, penaltiesSlider,pl.getPenalties());
        setDataShow(visionBox, visionSlider,pl.getVision());
        setDataShow(crossingBox, crossingSlider,pl.getCrossing());
        setDataShow(freeKickBox, freeKickSlider,pl.getFreekick());
        setDataShow(shortPassingBox, shortPassingSlider,pl.getShortpassing());
        setDataShow(longPassingBox, longPassingSlider,pl.getLongpassing());
        setDataShow(curveBox, curveSlider,pl.getCurve());
        setDataShow(agilityBox, agilitySlider,pl.getAgility());
        setDataShow(balanceBox, balanceSlider,pl.getBalance());
        setDataShow(reactionsBox, reactionsSlider,pl.getReactions());
        setDataShow(ballControlBox, ballControlSlider,pl.getBallcontrol());
        setDataShow(dribblingBox, dribblingSlider,pl.getDribbling());
        setDataShow(interceptionsBox, interceptionsSlider,pl.getInterceptions());
        setDataShow(headingBox, headingSlider,pl.getHeading());
        setDataShow(markingBox, markingSlider,pl.getMarking());
        setDataShow(standTackleBox, standTackleSlider,pl.getStandtackle());
        setDataShow(slidingTackleBox, slidingTackleSlider,pl.getSlidingtackle());



//
//        positioningBox.setText(String.valueOf(pl.getPositioning()));
//        finishingBox.setText(String.valueOf(pl.getFinishing()));
//        shotPowerBox.setText(String.valueOf(pl.getShotpower()));
//        longShotBox.setText(String.valueOf(pl.getLongshot()));
//        volleysBox.setText(String.valueOf(pl.getVolleys()));
//        penaltiesBox.setText(String.valueOf(pl.getPenalties()));
//
//        visionBox.setText(String.valueOf(pl.getVision()));
//        crossingBox.setText(String.valueOf(pl.getCrossing()));
//        freeKickBox.setText(String.valueOf(pl.getFreekick()));
//        shortPassingBox.setText(String.valueOf(pl.getShortpassing()));
//        longPassingBox.setText(String.valueOf(pl.getLongpassing()));
//        curveBox.setText(String.valueOf(pl.getCurve()));
//
//        agilityBox.setText(String.valueOf(pl.getAgility()));
//        balanceBox.setText(String.valueOf(pl.getBalance()));
//        reactionsBox.setText(String.valueOf(pl.getReactions()));
//        ballControlBox.setText(String.valueOf(pl.getBallcontrol()));
//        dribblingBox.setText(String.valueOf(pl.getDribbling()));
//
//        interceptionsBox.setText(String.valueOf(pl.getInterceptions()));
//        headingBox.setText(String.valueOf(pl.getHeading()));
//        markingBox.setText(String.valueOf(pl.getMarking()));
//        standTackleBox.setText(String.valueOf(pl.getStandtackle()));
//        slidingTackleBox.setText(String.valueOf(pl.getSlidingtackle()));
//
//        jumpingBox.setText(String.valueOf(pl.getJumping()));
//        strengthBox.setText(String.valueOf(pl.getStrength()));
//        aggressionBox.setText(String.valueOf(pl.getAggression()));


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
