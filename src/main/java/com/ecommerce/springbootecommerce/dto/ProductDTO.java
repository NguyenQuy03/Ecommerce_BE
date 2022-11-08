package com.ecommerce.springbootecommerce.dto;

import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends AbstractDTO<ProductDTO>{

    private String name;
    private Double price;
    private byte[] image;
    private String base64Image;
    private String description;
    private Integer stock;
    private Long accountId;
    private Long categoryId;
    
    public String getBase64Image() {
        base64Image = Base64.getEncoder().encodeToString(this.image);
        return base64Image;
    }
 
    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
    
};
