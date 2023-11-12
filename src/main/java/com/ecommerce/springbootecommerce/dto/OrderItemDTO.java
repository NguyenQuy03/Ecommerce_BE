package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.Locale;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO extends BaseDTO<OrderItemDTO>{

    private OrderDTO orders;

    private ProductItemDTO productItem;

    private long quantity;

    private double curPrice;

    private String totalPrice;

    private OrderStatus status;

    @SuppressWarnings("unused")
    private String formatedCurPrice;

    public String getFormatedCurPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(moneyType);
        numberFormat.format(this.curPrice);
        return numberFormat.format(this.curPrice);
    }
}
