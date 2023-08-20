package com.ecommerce.springbootecommerce.dto.product;

import java.text.NumberFormat;
import java.util.Locale;

import com.ecommerce.springbootecommerce.dto.BaseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemDTO extends BaseDTO<ProductItemDTO>{
    private String productId;
    private Long stock;
    private Long sold;

    private String image;

    private Double price;
    private String status;

    private String variationName;
    private String variationValue;

    private String intPrice;

    public String getIntPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat Format = NumberFormat.getCurrencyInstance(moneyType);
        Format.format(this.price);
        return Format.format(this.price);
    }
}
