package resources.controllers.functions;
import javafx.scene.control.Label;
import resources.controllers.screens.SignInController;

public class Users {


    public static void setUsername (Label typeUser){
        typeUser.setText("@"+SignInController.getInstance().username());
    }
    public static void setPassword (Label typePass){
        typePass.setText(SignInController.getInstance().password());
    }
    public static boolean getRole(){
        return SignInController.getInstance().role();
    }
}
