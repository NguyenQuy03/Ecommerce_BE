package com.ecommerce.springbootecommerce.constant;

public class SystemConstant {
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
    
    //JWT Constant

    public static final String JWT_SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final long JWT_ACCESS_TOKEN_EXPIRATION = 60000; //1mins (millisecond)
    public static final long JWT_REFRESH_TOKEN_EXPIRATION = 259200; //3days (second)
    
}
