package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.product.CurrencyType;
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

    private String price;
    private CurrencyType currencyType;

    private ProductStatus status;

    private String variation;

    private List<OrderItemEntity> orderItems;
}
