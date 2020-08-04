package resources.functions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class duplicated {

    public static void isAllDone(Boolean bothDone, JFXButton btn){
        if(bothDone){
            btn.setDisable(false);
        }else{
            btn.setDisable(true);
        }
    }
    public static boolean isEmptyField(Object obj, Label objLb, ImageView objImg ){

        if(obj instanceof JFXTextField){
            return checkingCorrection(obj,objLb,objImg);
        } else if(obj instanceof JFXPasswordField) {
           return  checkingCorrection(obj,objLb,objImg);
        }
        return  false;
    }

    public static boolean isConfirmRight(JFXPasswordField pwd, JFXPasswordField ckPwd, Label cfLb, ImageView cfImg){
        String pass = pwd.getText();
        String check = ckPwd.getText();
        if(check.length()==0){
            cfLb.setText("Please fulfill");
        } else if(check.equals(pass)){
            cfLb.setText("Matched");
            warningImage(true,cfLb,cfImg);
            return true;
        } else if(check.length()<pass.length()){
            cfLb.setText("Not enough");
        } else {
            cfLb.setText("Not match");
        }
        warningImage(false,cfLb,cfImg);
        return false;
    }

    public static void warningImage(boolean isCorrect, Label lb, ImageView img){
        lb.getStyleClass().clear();
        img.setVisible(true);
        lb.setVisible(true);
        if(!isCorrect){
            img.setImage(new Image("./resources/images/icon/alert-circle_red.png"));
            lb.getStyleClass().add("failed");
        }else {
            lb.getStyleClass().add("succeed");
            img.setImage(new Image("./resources/images/icon/check-circle_green.png"));
        }
    }

    public static boolean checkingCorrection(Object obj, Label lb, ImageView img){

        int length;
        boolean flag;

        if(obj instanceof JFXTextField){
            length = ((JFXTextField) obj).getText().length();
            flag = checkingLength(length,lb);
            warningImage(flag, lb, img);
            return flag;

        } else if(obj instanceof JFXPasswordField) {
            length = ((PasswordField)obj).getText().length();
            flag = checkingLength(length,lb);
            warningImage(flag, lb, img);
            return flag;
        }

        return false;
    }

    public static boolean checkingLength(int length, Label lb){
        if(length==0){
            lb.setText("Please fulfill");
        } else if(length>0 && length<3){
            lb.setText("At lease 3 characters");
        } else{
            lb.setText("Correct");
            return true;
        }
        return false;
    }
}
