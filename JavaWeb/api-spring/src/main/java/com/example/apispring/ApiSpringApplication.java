package com.example.apispring;

import com.example.apispring.controllers.AccountController;
import com.example.apispring.controllers.FeedController;
import com.example.apispring.models.Account;
import com.example.apispring.models.Feed;
import com.example.apispring.services.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class ApiSpringApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApiSpringApplication.class, args);
        Account account = new AccountController().fetch_account();
        List<Feed> feeds = new FeedController().fetch_feed(account);
    }

}
