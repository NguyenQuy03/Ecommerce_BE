package com.ecommerce.springbootecommerce.dto;

import java.util.List;
import java.util.Map;

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

    private Map<String, Object> additionalInfo;

    private boolean isVariational;

    private String variations;

    private ProductStatus status;

    private String specification;

    private List<String> images;

    private List<ProductItemDTO> productItems;
}
