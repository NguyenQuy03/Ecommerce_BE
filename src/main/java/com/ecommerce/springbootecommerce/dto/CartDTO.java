package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO extends BaseDTO<CartDTO> {
    
    private String status;
    private AccountEntity account;
    private List<OrderDTO> setOrders;
}
