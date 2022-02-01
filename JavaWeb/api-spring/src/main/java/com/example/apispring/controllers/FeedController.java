package com.example.apispring.controllers;

import com.example.apispring.models.Account;
import com.example.apispring.models.Feed;
import com.example.apispring.services.Connection;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class FeedController {

    public List<Feed> fetch_feed(Account account) {

        String feed_api = "https://api-sandbox.starlingbank.com/api/v2/feed/" +
                "account/"+account.getAccountUid() +
                "/category/"+account.getDefaultCategory() +
                "?changesSince="+account.getCreatedAt();


        List<Feed> feeds = new Connection().builder(feed_api, HttpMethod.GET).getFeeds();
        List<Feed> feeds_out = new ArrayList<>();
        for (Feed feed : feeds) {
            if (feed.getDirection().equals("OUT")){
                feeds_out.add(feed);
            }
        }
        System.out.println(feeds_out);
        return feeds_out;
    }
}
