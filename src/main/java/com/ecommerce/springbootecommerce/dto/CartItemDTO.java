package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemDTO extends BaseDTO<CartItemDTO> {
    private String cartId;

    private ProductItemDTO productItem;

    private long quantity;
}
