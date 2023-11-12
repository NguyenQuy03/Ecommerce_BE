package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO extends BaseDTO<OrderDTO>{
    
    private OrderStatus status;
    
    private String totalPrice;
    
    private List<OrderItemDTO> orderItems;
    
    private AccountDTO account;

    private CartDTO cart;

    private VoucherDTO shopVoucher;
}
