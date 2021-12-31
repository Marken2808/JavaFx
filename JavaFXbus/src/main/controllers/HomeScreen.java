package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.IntFunction;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HomeScreen implements Initializable {

    String gg_key = "AIzaSyA7bY7dE3iuxLeA8AO6pAL6x8svpXdcKeg";

//    String app_id = "5b6e691f" ;
//    String app_key = "0a9f8db966fcde24e95681c97c55a75d ";

    String app_id = "9281db1a" ;
    String app_key = "86dda2ae6c899b579364035ec136f7b0";

    @FXML
    private TextField textSearch;

    @FXML
    private Button searchBtn;

    @FXML
    private WebView displayMaps;

    ArrayList<JSONObject> nearestStops() {
        ArrayList<JSONObject> bothNearest = new ArrayList<>();
        for (int i = 0; i < member().size(); i++) {
            JSONObject new_obj = ((JSONObject) member().get(i));
            if((double) new_obj.get("distance") < 100){
                //System.out.println( new_obj.get("atcocode") + " has: " +  new_obj.get("distance"));
                bothNearest.add(new_obj);
            }
        }
        return bothNearest;

    }

    ArrayList<String> atcoLists = new ArrayList<>();

    ArrayList<String> atcocodeStops(){

        for (int i = 0; i < nearestStops().size(); i++){
            String atco = (String) (nearestStops().get(i)).get("atcocode");
            atcoLists.add(atco);
        }
        return atcoLists;
    }

//    String operator() {
//        String oper = (String) ((JSONObject)inforAll().get(0)).get("operator");
//        return oper;
//    }

    @FXML
    void letSearch(MouseEvent event) {

        System.out.println(atcocodeStops());
        //System.out.println(getDirection(atcocodeStops().get(1)));

    }

    String getDirection(String atcocode){
        String dir = null;
        for (int i = 0; i < inforStop(atcocode).size(); i++){
            dir = (String) ((JSONObject)(inforStop(atcocode).get(i))).get("dir");
            break;
        }
        return dir;
    }

    JSONArray inforStop(String atcocode){

        try {
            URL link = new URL("https://transportapi.com/v3/uk/bus/stop/" +atcocode+ "/live.json?" +
                    "app_id=" + app_id +
                    "&app_key=" + app_key +
                    "&group=no" +
                    "&nextbuses=yes");

            HttpURLConnection conn = (HttpURLConnection)link.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(link.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject)parse.parse(inline);
                JSONArray inforAll = (JSONArray) ((JSONObject)data_obj.get("departures")).get("all");


                return inforAll;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    JSONObject coordinate () {
        try {
            URL ggLink = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
                    "input="+textSearch.getText()+
                    "&inputtype=textquery" +
                    "&fields=photos,formatted_address,name,rating,opening_hours,geometry" +
                    "&key=" + gg_key);

            HttpURLConnection conn = (HttpURLConnection) ggLink.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(ggLink.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                JSONObject location = (JSONObject) ((JSONObject) ((JSONObject) ((JSONArray) data_obj.get("candidates")).get(0)).get("geometry")).get("location");
                displayMaps.getEngine().load("https://www.google.co.uk/maps/place/"+textSearch.getText());
                return location;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }


    JSONArray member(){
        Object lng = coordinate().get("lng");
        Object lat = coordinate().get("lat");

        try {
            URL link = new URL("https://transportapi.com/v3/uk/places.json?" +
                    "app_id=" + app_id +
                    "&app_key=" + app_key +
                    "&lat="+lat +
                    "&lon="+lng +
                    "&query="+ textSearch.getText() +
                    "&type=bus_stop");

            HttpURLConnection conn = (HttpURLConnection)link.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(link.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                JSONArray member = (JSONArray) data_obj.get("member");

                return member;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
