package com.example.apispring.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Feed {

    @JsonProperty("categoryUid")
    private String categoryUid;

    @JsonProperty("transactingApplicationUserUid")
    private String transactionUid;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("amount")
    private Map<String, Object> amount;

/*
    Nested Json (amount.minorUnits), set getter method below to call
*/
    private long minorUnits;

    public Feed() {}

    public Feed(String categoryUid, String transactionUid, String direction, long minorUnits) {
        this.categoryUid = categoryUid;
        this.transactionUid = transactionUid;
        this.direction = direction;
        this.minorUnits = minorUnits;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "categoryUid='" + categoryUid + '\'' +
                ", transactionUid='" + transactionUid + '\'' +
                ", direction='" + direction + '\'' +
                ", minorUnits=" + getMinorUnits() +
                '}';
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public String getTransactionUid() {
        return transactionUid;
    }

    public String getDirection() {
        return direction;
    }

    public long getMinorUnits() {
        if (amount != null)
            minorUnits = (int) amount.get("minorUnits");
        return minorUnits;
    }
}
