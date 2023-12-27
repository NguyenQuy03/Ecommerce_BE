package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO> {

    private AccountDTO account;
    private CategoryDTO category;

    private String name;

    private String image;

    private String description;

    private Long totalSold;

    private Long totalStock;

    private Double revenue;

    private String avgPrice;

    private boolean isVariational;

    private String variations;

    private ProductStatus status;

    private String specification;

    private List<ProductItemDTO> productItems;
}
