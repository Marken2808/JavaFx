package repositories;

import models.Account;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountRepo {

    private String account_api = "https://api-sandbox.starlingbank.com/api/v2/accounts";
    private String token = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lRTnnS2Med9yyw_kAwZpZKssJEoSu9lK5d8jkDDGceVGd8-rR2P_zqRzWZ_hJIHTaN6dR6ukvgyob-_MjNlb5uYhROSiFU2edyC6poCyYQNgznNozqwqOC9yVooQTL-mrD_V5bmuqvx8essk-khUdVsvBDJmZu1_GMXJ_pQ81D4RNYLlAzRUVFB2gqBrhYBiqNru1ImiJR5qe3MjHTPEUGBR5hUM2CCUeSegy8MXP5VtU7bnVtR1yAi2vjNGzsUshqEiDQS5wBzKoS2hK0UHTVmLqqzrKjRbDDMz0bKUOCkwZRzx3hLybxt3XccHjSO9FPzX9CRITtpLIckeeSWdPzAJcG7D4D1x6e8gKt4ju450j9zxp5WevuHsr8ZKF54RpObyQ_IZVQweUKFmaTSGlgMz2lujYqOFSZrRQtoRvTQajAAxa-7ukrt330BszWbnzbhZpBFlKqwoDKIvPU6T-rqjNWpEzdFTz0lRKLHBpNkb-cXIZEmQpTC7-58Ux4japJBR2ICni119PCb-K6ZUsuyKm7uRPIZpsGcBrmrCq6kJv4g2KYJkIoI9COSIl-QpavsneIvaIdsnDDQMs7r120vSTu3dIt4bRrwVWO4h3NYo_V5TGRbu4KHCSoBZDuKZTVnWCKm28aOfA7VGWWIkJ38A7ijF5Tr8CA_m4GL2OQ5ccnPg1jqPTFxYePJXJXbxRa1djEXZlfisiENaWKLJ-2BwnhKccPvBhH_H9XDBWP7Q_shufY_si3wwn_rOe1pfjLmPZ2riIlHz4JgN21wOZevyyK1Rj9e0PtjzeWV__gJOhDlk9gUAAA.erZHKYuWRGK-Gk6CLFeAMcFpx_6uIDD5Zd5VOD7DEAuKE171HK_UTpOfJ6A3y38cmEINWRCVweD0mphaZVNlK7fAPYD66qE_cj4MLnVZncgsM8VXD9q5hGMDV16HeK0rOysrij0edPECXBQJGuy7vNgXBJri1xEfYNJzs4Ir5XjwsnDOvL64Co-jvotbjzpLMIsbmRqrnZZwq99Unl-oBnkoVcwQHPcRlpFIeBWuR5hWXSN2ys6MT7UDNkAerQdfgyHwveMpIqjQ4zhVukDrW9doDEfTd-If3Jzki6k9Ce29rv5TjPZUpkCsiLlp6HSNnlXvSrI77m1x5ww00OG3_AhPvYbrxEOpYeUO6CuFTLRNoE5ZKQbOh1JC4RAtOLWpfvVrltWdeHNQZYUGU9y590Uywhtl2BHr4Ez39Vz6PiORDja0lu1p8zJQHyqF-ZI71p_qJjNamtuKTt51nQ2WeQW2-Eq94_pN5d_PXc1npOC8KsowrYMPhC5v5fYKM-y7FqrnER8LpmDXcxPguDUfCfEaRnIaAFoogjFNF7B81wOSpwkcbuV_ze0AWbM2VgQqDS3TfNhCezbyjYIj-z_jXRrjHl-pzmF5ze0L_zNJKFkL9IXFS4H1ZmmgoWwJO0IDC-h4weCb66yLJQACThKiMVIhZ5UqZOaeXe2vwc38PG4";

    public Account fetch_account() throws Exception {
        URL url = new URL(account_api);
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

            return (read_account(builder.toString()));
        }
    }

    public Account read_account(String inline) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inline);
        JSONArray jsonArray = (JSONArray) jsonObject.get("accounts");

        Account chosen = null;
        List<Account> accounts = new ArrayList<>();
        jsonArray.forEach(account -> accounts.add(parseObject((JSONObject) account)));
//        System.out.println(accounts);

        for (Account account : accounts) {
            if (account.getAccountType().equals("PRIMARY")){
                chosen = account;
            }
        }
        return chosen;
    }

    private Account parseObject(JSONObject obj) {
        return new Account().fromJson(obj);
    }
}
