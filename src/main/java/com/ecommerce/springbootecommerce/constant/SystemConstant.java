package com.ecommerce.springbootecommerce.constant;

public class SystemConstant {
    private SystemConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    public static final boolean ACTIVE_STATUS = true;
    public static final boolean INACTIVE_STATUS = false;
    
    public static final String STRING_ACTIVE_STATUS = "ACTIVE";
    public static final String STRING_DELIVERED_ORDER = "DELIVERED";
    
    public static final String ROLE_BUYER = "BUYER";
    public static final String ROLE_SELLER = "SELLER";
    
    public static final String SOLD_OUT_PRODUCT = "SOLD OUT";
    public static final String REMOVED_PRODUCT = "REMOVED";
    public static final String DELETED_PRODUCT = "DELETED";
    
    public static final String ACCESS_EXCEPTION = "You do not have permission to perform this action";
    
    public static final String PRODUCT_NOT_AVAILABLE = "This product was no longer available";

    /* ALERT TYPE */
    public static final String ALERT_DANGER = "danger";
    public static final String ALERT_INFO = "info";

    /* JWT */
    public static final long JWT_ACCESS_TOKEN_EXPIRATION = 30000; // 30secs
    public static final int JWT_COOKIE_ACCESS_TOKEN_EXPIRATION = 3600; // 1h
    public static final long JWT_REFRESH_TOKEN_EXPIRATION = 259200; // 3days
    
}
