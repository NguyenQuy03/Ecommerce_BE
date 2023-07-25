package com.ecommerce.springbootecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO<OrderDTO>{
    
    private String status;
    private long quantity;
    private String imageBase64;
    private String curPrice;
    private ProductDTO product;
    private CartDTO cart;
    
    public String getImageBase64() {
        imageBase64 = Base64.getEncoder().encodeToString(this.product.getImage().getData());
        return imageBase64;
    }
    
    public String getCurPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat $Format = NumberFormat.getCurrencyInstance(moneyType);
        return $Format.format(this.product.getPrice());
    }
}
