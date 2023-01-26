package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;
import java.util.Set;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO<OrderDTO>{
    
    private String status;
    private Long quantity;
    private String imageBase64;
    @SuppressWarnings("unused")
    private String intPrice;
    private AccountEntity account;
    private ProductEntity product;
    private Set<CartEntity> carts;
    
    public String getImageBase64() {
        imageBase64 = Base64.getEncoder().encodeToString(this.product.getImage());
        return imageBase64;
    }
    
    public String getIntPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat $Format = NumberFormat.getCurrencyInstance(moneyType);
        $Format.format(this.product.getPrice());
        return $Format.format(this.product.getPrice());
    }
}
