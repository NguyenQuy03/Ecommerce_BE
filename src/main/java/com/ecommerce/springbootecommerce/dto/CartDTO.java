package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends BaseDTO<CartDTO> {
    private AccountDTO account;

    private List<OrderDTO> orders;

    private VoucherDTO platformVoucher;
}
