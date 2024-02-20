package com.ecommerce.springbootecommerce.constant.enums.transaction;

import java.util.Arrays;
import java.util.List;

public enum TransactionStatus {
    SUCCESS,
    FAILUE;

    public static List<TransactionStatus> getInActiveStatus() {
        return Arrays.asList(SUCCESS, FAILUE);
    }
}
