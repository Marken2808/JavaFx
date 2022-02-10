package com.mycompany.myapp.domain.enumeration;

/**
 * The PaymentMethod enumeration.
 */
public enum PaymentMethod {
    CREDIT_CARD("card"),
    IDEAL("ideal"),
    CASH_ON_DELIVERY,
    PAYPAL;

    private String value;

    PaymentMethod() {}

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
