import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class FetchApi {

    private String link = "https://api.covid19api.com/summary";

    public void fetch() throws Exception {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int code = conn.getResponseCode();

        if (code != 200) {
            throw new RuntimeException("HttpResponseCode: " + code);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                inline += scanner.nextLine();
            }

            scanner.close();

            //
            read(inline);
        }

    }

    public void read(String inline) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inline);

        JSONObject obj = (JSONObject) jsonObject.get("Global");

        Global global = new Global().fromJson(obj);
        System.out.println(global.toString());

        JSONArray jsonArray = (JSONArray) jsonObject.get("Countries");

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject new_obj = (JSONObject) jsonArray.get(i);

            if (new_obj.get("Slug").equals("albania")) {
                System.out.println("NewConfirmed: " + new_obj.get("NewConfirmed"));
                break;
            }
        }



    }
}
