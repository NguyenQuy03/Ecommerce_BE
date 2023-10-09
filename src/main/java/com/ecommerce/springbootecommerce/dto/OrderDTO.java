package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO<OrderDTO>{

    private AccountDTO account;

    private String status;

    private String totalPrice;

    private List<OrderItemDTO> orderItems;

    private List<Long> cartItemIds;

    private Long cartId;

    private ProductItemDTO productItem;
}
