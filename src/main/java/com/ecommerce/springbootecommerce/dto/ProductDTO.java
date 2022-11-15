package com.ecommerce.springbootecommerce.dto;

import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{

    private String name;
    private Double price;
    private byte[] image;
    private String imageBase64;
    private String description;
    private Integer stock;
    private Long accountId;
    private Long categoryId;

    public String getImageBase64() {
        imageBase64 = Base64.getEncoder().encodeToString(this.image);
        return imageBase64;
    }
    
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
};
