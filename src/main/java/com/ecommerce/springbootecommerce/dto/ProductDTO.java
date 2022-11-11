package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{

    private String name;
    private Double price;
    private byte[] image;
    private String description;
    private Integer stock;
    private Long accountId;
    private Long categoryId;

};
