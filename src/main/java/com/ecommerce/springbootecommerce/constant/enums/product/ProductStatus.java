package com.ecommerce.springbootecommerce.constant.enums.product;

import java.util.Arrays;
import java.util.List;

public enum ProductStatus {
    ACTIVE,
    INACTIVE,
    SOLD_OUT,
    REMOVED;

    public static List<ProductStatus> getInActiveStatus() {
        return Arrays.asList(INACTIVE, SOLD_OUT, REMOVED);
    }
}
