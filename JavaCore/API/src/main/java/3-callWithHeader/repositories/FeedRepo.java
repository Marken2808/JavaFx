package repositories;

import models.Account;
import models.Feed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedRepo {
    private String token = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lRTnnS2Med9yyw_kAwZpZKssJEoSu9lK5d8jkDDGceVGd8-rR2P_zqRzWZ_hJIHTaN6dR6ukvgyob-_MjNlb5uYhROSiFU2edyC6poCyYQNgznNozqwqOC9yVooQTL-mrD_V5bmuqvx8essk-khUdVsvBDJmZu1_GMXJ_pQ81D4RNYLlAzRUVFB2gqBrhYBiqNru1ImiJR5qe3MjHTPEUGBR5hUM2CCUeSegy8MXP5VtU7bnVtR1yAi2vjNGzsUshqEiDQS5wBzKoS2hK0UHTVmLqqzrKjRbDDMz0bKUOCkwZRzx3hLybxt3XccHjSO9FPzX9CRITtpLIckeeSWdPzAJcG7D4D1x6e8gKt4ju450j9zxp5WevuHsr8ZKF54RpObyQ_IZVQweUKFmaTSGlgMz2lujYqOFSZrRQtoRvTQajAAxa-7ukrt330BszWbnzbhZpBFlKqwoDKIvPU6T-rqjNWpEzdFTz0lRKLHBpNkb-cXIZEmQpTC7-58Ux4japJBR2ICni119PCb-K6ZUsuyKm7uRPIZpsGcBrmrCq6kJv4g2KYJkIoI9COSIl-QpavsneIvaIdsnDDQMs7r120vSTu3dIt4bRrwVWO4h3NYo_V5TGRbu4KHCSoBZDuKZTVnWCKm28aOfA7VGWWIkJ38A7ijF5Tr8CA_m4GL2OQ5ccnPg1jqPTFxYePJXJXbxRa1djEXZlfisiENaWKLJ-2BwnhKccPvBhH_H9XDBWP7Q_shufY_si3wwn_rOe1pfjLmPZ2riIlHz4JgN21wOZevyyK1Rj9e0PtjzeWV__gJOhDlk9gUAAA.erZHKYuWRGK-Gk6CLFeAMcFpx_6uIDD5Zd5VOD7DEAuKE171HK_UTpOfJ6A3y38cmEINWRCVweD0mphaZVNlK7fAPYD66qE_cj4MLnVZncgsM8VXD9q5hGMDV16HeK0rOysrij0edPECXBQJGuy7vNgXBJri1xEfYNJzs4Ir5XjwsnDOvL64Co-jvotbjzpLMIsbmRqrnZZwq99Unl-oBnkoVcwQHPcRlpFIeBWuR5hWXSN2ys6MT7UDNkAerQdfgyHwveMpIqjQ4zhVukDrW9doDEfTd-If3Jzki6k9Ce29rv5TjPZUpkCsiLlp6HSNnlXvSrI77m1x5ww00OG3_AhPvYbrxEOpYeUO6CuFTLRNoE5ZKQbOh1JC4RAtOLWpfvVrltWdeHNQZYUGU9y590Uywhtl2BHr4Ez39Vz6PiORDja0lu1p8zJQHyqF-ZI71p_qJjNamtuKTt51nQ2WeQW2-Eq94_pN5d_PXc1npOC8KsowrYMPhC5v5fYKM-y7FqrnER8LpmDXcxPguDUfCfEaRnIaAFoogjFNF7B81wOSpwkcbuV_ze0AWbM2VgQqDS3TfNhCezbyjYIj-z_jXRrjHl-pzmF5ze0L_zNJKFkL9IXFS4H1ZmmgoWwJO0IDC-h4weCb66yLJQACThKiMVIhZ5UqZOaeXe2vwc38PG4";

    public List<Feed> fetch_feed(Account account) throws Exception {
        String feed_api = "https://api-sandbox.starlingbank.com/api/v2/feed/" +
                "account/"+account.getAccountUid() +
                "/category/"+account.getDefaultCategory() +
                "?changesSince="+account.getCreatedAt();

        URL url = new URL(feed_api);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.addRequestProperty("User-Agent","TUAN CONG NGUYEN");
        conn.setRequestProperty("Authorization", token);
        conn.connect();

        int code = conn.getResponseCode();

        if (code != 200) {
            throw new RuntimeException("HttpResponseCode: " + code);
        } else {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inline;
            StringBuilder builder = new StringBuilder();

            while ((inline = bufferedReader.readLine()) != null) {
                builder.append(inline);
            }
            bufferedReader.close();

            return read_feed(builder.toString());
        }
    }

    public List<Feed> read_feed(String inline) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inline);
        JSONArray jsonArray = (JSONArray) jsonObject.get("feedItems");

        List<Feed> feeds = new ArrayList<>();
        jsonArray.forEach(feed -> feeds.add(parseObject((JSONObject) feed)));

        List<Feed> feeds_out = new ArrayList<>();
        feeds.forEach(feed -> {
            if (feed.getDirection().equals("OUT")){
                feeds_out.add(feed);
            }
        });
        return feeds_out;
    }

    private Feed parseObject(JSONObject obj) {
        return new Feed().fromJson(obj);
    }
}
