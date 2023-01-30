package com.ecommerce.springbootecommerce.dto;

import java.util.Set;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends BaseDTO<CartDTO> {
    private String status;
    
    private AccountEntity account;
    
    private Set<OrderEntity> setOrders;
}
