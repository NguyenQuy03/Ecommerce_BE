package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends AbstractDTO<ProductDTO>{

    private String name;
    private Double price;
    private Byte[] image;
    private String description;
    private Integer stock;
    private Long accountId;
    private Long categoryId;
    private String categoryCode;

}
