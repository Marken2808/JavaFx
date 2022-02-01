package com.example.apispring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Wrapper {
    @JsonProperty("accounts")
    private List<Account> accounts;

    @JsonProperty("feedItems")
    private List<Feed> feeds;


    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }
}
