package resources.controllers.functions;
import javafx.scene.control.Label;
import resources.controllers.screens.SignInController;

import java.util.ArrayList;

public class Users {

    private String role;
    private String username;
    private String password;
    private String profilename;


    public Users(String role, String username, String password, String profilename) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.profilename = profilename;
    }

    public Users(){}

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public static void checkProfileName (Label typeUser){
        typeUser.setText("@"+checkCurrentUser().get(3));
    }

    public static void checkPassword (Label typePass){
        typePass.setText(checkCurrentUser().get(2));
    }

    public static String checkRole (){
        return checkCurrentUser().get(0);
    }

    public static ArrayList<String> checkCurrentUser() {
        return SignInController.getInstance().createConnectionWithRole();
    }
}
