package com.example.apispring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty("accountUid")
    private String accountUid;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("defaultCategory")
    private String defaultCategory;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("name")
    private String name;

    public Account() {}

    public Account(
            String accountUid,
            String accountType,
            String defaultCategory,
            String currency,
            String createdAt,
            String name
    ) {
        this.accountUid = accountUid;
        this.accountType = accountType;
        this.defaultCategory = defaultCategory;
        this.currency = currency;
        this.createdAt = createdAt;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountUid='" + accountUid + '\'' +
                ", accountType='" + accountType + '\'' +
                ", defaultCategory='" + defaultCategory + '\'' +
                ", currency='" + currency + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getAccountUid() {
        return accountUid;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getDefaultCategory() {
        return defaultCategory;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }
}
