package com.ecommerce.springbootecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO<OrderDTO>{

    private AccountDTO account;

    private String status;
    private double totalPrice;

    private List<OrderItemDTO> orderItems;
    
    public String getTotalPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat $Format = NumberFormat.getCurrencyInstance(moneyType);
        return $Format.format(this.totalPrice);
    }
}
