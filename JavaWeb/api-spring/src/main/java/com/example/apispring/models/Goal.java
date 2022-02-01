package com.example.apispring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Goal {
    @JsonProperty("savingsGoalUid")
    private String savingsGoalUid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("target")
    private Map<String, Object> target;

    private long minorUnits_target;

    @JsonProperty("totalSaved")
    private Map<String, Object> totalSaved;

    private long minorUnits_total;

    @JsonProperty("savedPercentage")
    private Long savedPercentage;




}
