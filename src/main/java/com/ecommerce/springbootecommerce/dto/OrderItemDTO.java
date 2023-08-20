package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends BaseDTO<OrderItemDTO>{

    private String orderId;

    private ProductItemDTO productItem;

    private long quantity;

    private double curPrice;
}
