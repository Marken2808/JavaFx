package resources.controllers.functions;

import java.util.HashMap;

public class Preferences {

    private static String username;
    private static String password;

    public static HashMap<String, Preferences> mapList = new HashMap<String, Preferences>();

    public Preferences() {
        username = "admin";
        password = "pass";
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Preferences.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Preferences.password = password;
    }

    public static HashMap<String, Preferences> getMapList() {
        return mapList;
    }

    public static void setMapList(HashMap<String, Preferences> mapList) {
        Preferences.mapList = mapList;
    }
}
