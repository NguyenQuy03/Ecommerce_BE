package com.ecommerce.springbootecommerce.constant.enums.product;

public enum CurrencyType {
    US("USD"),
    VN("VND");

    private String currencyType;

    CurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    private String getCurrencyType() {
        return this.currencyType;
    }

    public static CurrencyType get(String type) {
        for (CurrencyType item : CurrencyType.values()) {
            if (type.equals(item.getCurrencyType())) {
                return item;
            }
        }

        return null;
    }
}
