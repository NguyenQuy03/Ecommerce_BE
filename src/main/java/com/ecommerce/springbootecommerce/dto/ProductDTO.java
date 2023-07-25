package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class ProductDTO extends BaseDTO<ProductDTO>{
    private String name;

    private String brand;
    
    private Double price;
    
    private String intPrice;
    
    private Binary image;
    
    private Long sold;

    private String imageBase64;

    private String description;
    private String details;
    
    private Long stock;
    private String accountId;

    private String categoryId;
    
    private String status;
    private long quantity;

    private List<Map<String, String>> specifications;

//    public ProductDTO putSpecial(String key, String value) {
//        specials.put(key, value);
//        return this;
//    }
    public String getImageBase64() {
        if(image == null) return "";
        return Base64.getEncoder().encodeToString(image.getData());
    }

    public String getImageBase64Data() {
        return this.imageBase64;
    }
    
    public String getIntPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat Format = NumberFormat.getCurrencyInstance(moneyType);
        Format.format(this.getPrice());
        return Format.format(this.getPrice());
    }
}
