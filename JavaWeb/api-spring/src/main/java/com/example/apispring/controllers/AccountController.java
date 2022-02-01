package com.example.apispring.controllers;

import com.example.apispring.models.Account;
import com.example.apispring.services.Connection;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public class AccountController {

    private String account_api = "https://api-sandbox.starlingbank.com/api/v2/accounts";

    public Account fetch_account() {
        List<Account> accounts = new Connection().builder(account_api, HttpMethod.GET).getAccounts();
        for (Account account : accounts) {
            if (account.getAccountType().equals("PRIMARY")){
                return account;
            }
        }
        return null;
    }
}
