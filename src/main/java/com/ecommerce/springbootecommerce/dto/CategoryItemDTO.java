package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryItemDTO extends BaseDTO<CategoryItemDTO> {

    private CartDTO cart;

    private ProductItemDTO productItem;

    private long quantity;
}
