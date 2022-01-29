package Zuhlke;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class zuhlke
{

    public static void main (String[] args) throws IOException, ParseException {
        String str = ("https://jsonmock.hackerrank.com/api/countries/search?name="+s);
        URL url = new URL(str);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.connect();

        Scanner scan = new Scanner(url.openStream());
        while(scan.hasNext()){
            String temp = scan.nextLine();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(temp);
            JSONArray array = json.getJSONArray("data");
        }
    }
}