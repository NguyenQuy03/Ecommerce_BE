package com.ecommerce.springbootecommerce.constant;

public class AlertConstant {
    private AlertConstant() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final String ALERT_DANGER = "danger";
    public static final String ALERT_INFO = "info";
    public static final String ALERT_WARN = "warn";
    public static final String ALERT_MESSAGE_LOGIN_FAILURE = "Your username or password is incorrect";
    public static final int ALERT_MESSAGE_LOGIN_EXPIRATION = 5;
    public static final String ALERT_MESSAGE_TOKEN_EXPIRED = "Session expired. Please re-login";
    public static final String ALERT_MESSAGE_NOT_PERMISSION = "Access denied. Please re-login";
    public static final String ALERT_WRONG_PASSWORD = "Password must match";
    public static final String ALERT_ACCOUNT_EXISTED = "The user name has existed, please choose another.";
    public static final String ALERT_EMAIL_EXISTED = "The email has existed, please choose another.";
    public static final String ALERT_SUCCESS_REGISTATION = "Success! Your registration is now complete.";

    public static final String ALERT_WRONG_REGEX = "Your username have to match 0-9 or a-z[A-Z].";

}
