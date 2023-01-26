package com.ecommerce.springbootecommerce.dto;

import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    
    private Long id;
    private Long quantity;
    private String name;
    private Long price;
    private byte[] image;
    private String imageBase64;
    
    public String getImageBase64() {
        imageBase64 = Base64.getEncoder().encodeToString(this.image);
        return imageBase64;
    }
}
