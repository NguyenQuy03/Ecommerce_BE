package com.ecommerce.springbootecommerce.dto;

import java.util.Set;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO<OrderDTO>{
    
    private String status;
    private Long quantity;
    private AccountEntity account;
    private ProductEntity product;
    private Set<CartEntity> carts;
}
