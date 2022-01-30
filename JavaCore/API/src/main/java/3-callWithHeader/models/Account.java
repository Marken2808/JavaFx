package models;

public class Account {

    private String accountUid;
    private String accountType;
    private String defaultCategory;
    private String currency;
    private String createdAt;
    private String name;

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
}
