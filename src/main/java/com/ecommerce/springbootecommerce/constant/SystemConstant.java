package com.ecommerce.springbootecommerce.constant;

public class SystemConstant {
    private SystemConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    public static final boolean ACTIVE_STATUS = true;
    public static final boolean INACTIVE_STATUS = false;

    public static final String ROLE_BUYER = "BUYER";
    public static final String ROLE_SELLER = "SELLER";
    public static final String ACCESS_EXCEPTION = "You do not have permission to perform this action";

    public static final String PRODUCT_NOT_AVAILABLE = "This product was no longer available";

    public static final String COOKIE_JWT_HEADER = "JWT";
    public static final String TOKEN_TYPE = "Bearer ";

    /*STATUS*/
    public static final String STRING_ACTIVE_STATUS = "ACTIVE";
    public static final String STRING_DELIVERED_ORDER = "DELIVERED";
    public static final String SOLD_OUT_PRODUCT = "SOLD OUT";
    public static final String REMOVED_PRODUCT = "REMOVED";
    public static final String INACTIVE_PRODUCT = "INACTIVE";
}
