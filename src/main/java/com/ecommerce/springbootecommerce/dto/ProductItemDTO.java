package com.ecommerce.springbootecommerce.dto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemDTO extends BaseDTO<ProductItemDTO> {
    private ProductDTO product;

    private Long stock;
    private Long sold;

    private String image;

    private long quantity;

    private Double price;
    private ProductStatus status;

    private String variation;

    private List<OrderItemEntity> orderItems;

    @SuppressWarnings("unused")
    private String StringPrice;

    public String getStringPrice() {
        Locale moneyType = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(moneyType);
        numberFormat.format(this.price);
        return numberFormat.format(this.price);
    }
}
