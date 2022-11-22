package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{

    @NotBlank(message = "Please enter this field")
    private String name;
    
    @NotNull(message = "Please enter this field")
    private Double price;
    
    @SuppressWarnings("unused")
    private String intPrice;
    
    private byte[] image;
    private String imageBase64;
    
    @NotBlank(message = "Please enter this field")
    private String description;
    
    @NotNull(message = "Please enter this field")
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
    
    public String getIntPrice() {
        Locale vnd = new Locale("vi", "VN");
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(vnd);
        vndFormat.format(this.getPrice());
        return vndFormat.format(this.getPrice());
    }
    
    public void setIntPrice(String intPrice) {
        this.intPrice = intPrice;
    }
};
