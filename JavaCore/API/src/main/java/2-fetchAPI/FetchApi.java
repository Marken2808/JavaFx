import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

//        JSONObject obj_global = (JSONObject) jsonObject.get("Global");
//        Global global = new Global().fromJson(obj_global);
//        System.out.println(global.toString());

        JSONArray jsonArray = (JSONArray) jsonObject.get("Countries");
//        jsonArray.forEach(country -> parseObject((JSONObject) country));

        List<Country> countries = new ArrayList<>();
        jsonArray.forEach(country -> {
            countries.add(parseObject((JSONObject) country));
        });

        System.out.println(countries);
    }

    private Country parseObject(JSONObject obj)
    {
        return new Country().fromJson(obj);
    }
}
