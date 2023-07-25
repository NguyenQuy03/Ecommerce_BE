package com.ecommerce.springbootecommerce.constant;

public class RedisConstant {
    private RedisConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final String REDIS_JWT_BRANCH = "JWT:";
    public static final String REDIS_USER_INFO = "User_info:";

    public static final String REDIS_USER_INFO_QUANTITY_ORDER = "quantityOrder";

}
