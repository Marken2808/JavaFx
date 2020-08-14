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
                playerLists.add(
                    new Players(
                        rs.getString("name"),
                        rs.getString("club"),
                        rs.getString("position"),
                        rs.getInt("acceleration"),
                        rs.getInt("sprintspeed"),
                        rs.getInt("positioning"),
                        rs.getInt("finishing"),
                        rs.getInt("shotpower"),
                        rs.getInt("longshot"),
                        rs.getInt("volleys"),
                        rs.getInt("penalties"),
                        rs.getInt("vision"),
                        rs.getInt("crossing"),
                        rs.getInt("freekick"),
                        rs.getInt("shortpassing"),
                        rs.getInt("longpassing"),
                        rs.getInt("curve"),
                        rs.getInt("agility"),
                        rs.getInt("balance"),
                        rs.getInt("reactions"),
                        rs.getInt("ballcontrol"),
                        rs.getInt("dribbling"),
                        rs.getInt("interceptions"),
                        rs.getInt("heading"),
                        rs.getInt("marking"),
                        rs.getInt("standtackle"),
                        rs.getInt("slidingtackle"),
                        rs.getInt("jumping"),
                        rs.getInt("strength"),
                        rs.getInt("aggression")
                    )
                );
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
        return playerLists;
    }

}
