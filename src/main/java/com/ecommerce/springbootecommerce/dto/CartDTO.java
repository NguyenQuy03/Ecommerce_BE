package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends BaseDTO<CartDTO> {
    private AccountDTO account;

}
