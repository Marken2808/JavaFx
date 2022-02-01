package repositories;

import models.Account;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import services.Connection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountRepo {

    private String account_api = "https://api-sandbox.starlingbank.com/api/v2/accounts";

    public Account fetch_account() throws Exception {
        String fromJson = new Connection().builder(account_api);
        return read_account(fromJson);
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
