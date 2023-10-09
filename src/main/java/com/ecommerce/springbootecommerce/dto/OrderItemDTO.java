package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.Locale;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends BaseDTO<OrderItemDTO>{

    private OrderDTO order;

    private ProductItemDTO productItem;

    private long quantity;

    private double curPrice;

    private String totalPrice;

    private String status;

    private String formatedCurPrice;

    public String getFormatedCurPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat Format = NumberFormat.getCurrencyInstance(moneyType);
        Format.format(this.curPrice);
        return Format.format(this.curPrice);
    }
}
