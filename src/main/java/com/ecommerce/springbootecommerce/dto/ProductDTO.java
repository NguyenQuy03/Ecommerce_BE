package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{

    @NotBlank(message = "Please enter product's name!")
    private String name;
    
    @NotNull(message = "Please enter product's price!")
    private Double price;
    
    @SuppressWarnings("unused")
    private String intPrice;
    
    private byte[] image;
    
    private Long sold;
    
    private MultipartFile imageFile;
    private String imageBase64;
    
    @NotBlank(message = "Please enter product's description!")
    private String description;
    
    @NotBlank(message = "Please enter product's details!")
    private String details;
    
    @NotNull(message = "Please enter product's stock!")
    private Long stock;
    private Long accountId;
    
    @NotNull(message = "Please enter product's category!")
    private Long categoryId;
    
    private String status;
    private Long quantity;

    public String getImageBase64() {
        imageBase64 = Base64.getEncoder().encodeToString(this.image);
        return imageBase64;
    }
    
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
    
    public String getIntPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat $Format = NumberFormat.getCurrencyInstance(moneyType);
        $Format.format(this.getPrice());
        return $Format.format(this.getPrice());
    }
    
    public void setIntPrice(String intPrice) {
        this.intPrice = intPrice;
    }
};
