package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO extends BaseDTO<CartDTO> {
    private String accountId;

    private List<CartItemDTO> cartItems;
}
