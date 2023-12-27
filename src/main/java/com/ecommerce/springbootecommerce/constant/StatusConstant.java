package com.ecommerce.springbootecommerce.constant;

public class StatusConstant {
    private StatusConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final boolean BOOLEAN_ACTIVE_STATUS = true;
    public static final boolean BOOLEAN_INACTIVE_STATUS = false;

    public static final String STRING_ACTIVE_STATUS = "ACTIVE";
    public static final String STRING_INACTIVE_STATUS = "INACTIVE";
}
