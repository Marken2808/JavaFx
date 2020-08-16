package resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import resources.controllers.functions.Players;
import resources.controllers.functions.Users;

import java.sql.*;

public class mySQLconnection {

    public static Connection connection = null;
    public static PreparedStatement pst = null;

    public static Connection ConnectDataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/cardfx", "root", "zxcvbnm");
            //JOptionPane.showMessageDialog(null,"Connection Established");
            return connection;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }

    public static ObservableList<Users> getUserDataOnTable(){
        ObservableList<Users> userLists = FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("Select * from users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                userLists.add(new Users(
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("profilename")
                ));
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
        return userLists;
    }

    public static ObservableList<Players> getPlayerDataOnTable(){
        ObservableList<Players> playerLists =  FXCollections.observableArrayList();
        try {
            pst = connection.prepareStatement("Select * from players");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
//                int test = rs.getInt("acceleration");
//                System.out.println("acceleration: "+test);
                Players plStat = new Players();
                    plStat.setPname(rs.getString("name"));
                    plStat.setPclub(rs.getString("club"));
                    plStat.setPosition(rs.getString("position"));
                    plStat.setAcceleration(rs.getInt("acceleration"));
                    plStat.setSprintspeed(rs.getInt("sprintspeed"));
                    plStat.setPositioning(rs.getInt("positioning"));
                    plStat.setFinishing(rs.getInt("finishing"));
                    plStat.setShotpower(rs.getInt("shotpower"));
                    plStat.setLongshot(rs.getInt("longshot"));
                    plStat.setVolleys(rs.getInt("volleys"));
                    plStat.setPenalties(rs.getInt("penalties"));
                    plStat.setVision(rs.getInt("vision"));
                    plStat.setCrossing(rs.getInt("crossing"));
                    plStat.setFreekick(rs.getInt("freekick"));
                    plStat.setShortpassing(rs.getInt("shortpassing"));
                    plStat.setLongpassing(rs.getInt("longpassing"));
                    plStat.setCurve(rs.getInt("curve"));
                    plStat.setAgility(rs.getInt("agility"));
                    plStat.setBalance(rs.getInt("balance"));
                    plStat.setReactions(rs.getInt("reactions"));
                    plStat.setBallcontrol(rs.getInt("ballcontrol"));
                    plStat.setDribbling(rs.getInt("dribbling"));
                    plStat.setInterceptions(rs.getInt("interceptions"));
                    plStat.setHeading(rs.getInt("heading"));
                    plStat.setMarking(rs.getInt("marking"));
                    plStat.setStandtackle(rs.getInt("standtackle"));
                    plStat.setSlidingtackle(rs.getInt("slidingtackle"));
                    plStat.setJumping(rs.getInt("jumping"));
                    plStat.setStrength(rs.getInt("strength"));
                    plStat.setAggression(rs.getInt("aggression"));


                //Players plInfo = new Players();


                playerLists.addAll(plStat);

            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
        return playerLists;
    }

}
