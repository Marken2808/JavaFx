package models;

import org.json.simple.JSONObject;

public class Account {

    private String accountUid;
    private String accountType;
    private String defaultCategory;
    private String currency;
    private String createdAt;
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

    public Account fromJson(JSONObject obj) {
        this.accountUid =  (String) obj.get("accountUid");
        this.accountType = (String) obj.get("accountType");
        this.defaultCategory = (String) obj.get("defaultCategory");
        this.currency = (String) obj.get("currency");
        this.createdAt = (String) obj.get("createdAt");
        this.name = (String) obj.get("name");

        return new Account(accountUid, accountType, defaultCategory, currency, createdAt, name);
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
