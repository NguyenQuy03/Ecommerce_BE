package com.ecommerce.springbootecommerce.constant;

public class SystemConstant {
    private SystemConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
    public static final String ROLE_BUYER = "BUYER";
    public static final String ROLE_SELLER = "SELLER";
    public static final String ROLE_MANAGER = "MANAGER";
    
    public static final String ACCESS_EXCEPTION = "You do not have permission to perform this action";

    public static final String PRODUCT_NOT_AVAILABLE = "This product was no longer available";

    public static final String COOKIE_JWT_HEADER = "JWT";
    public static final String TOKEN_JWT_TYPE = "Bearer ";
    
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static final String QUANTITY_PRODUCT_DTO = "quantityProduct";
    public static final String QUANTITY_VOUCHER_DTO = "quantityVoucher";
    
    public static final String ACCOUNT_DTO = "account";

    public static final String LOGIN_FAILURE_DTO = "loginFailure";
    
    public static final String LOGIN_URL = "/login";
    
    public static final String DEFAULT_PAGE_SIZE = "2";

}
