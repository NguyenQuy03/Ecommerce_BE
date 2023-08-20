package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryItemDTO extends BaseDTO<CategoryItemDTO> {

    private String cartId;

    private String productItemId;

    private long quantity;
}
