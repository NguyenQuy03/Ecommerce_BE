package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO extends BaseDTO<CartItemDTO> {
    private CartDTO cart;

    private String username;

    private ProductItemDTO productItem;

    private long quantity;
}
