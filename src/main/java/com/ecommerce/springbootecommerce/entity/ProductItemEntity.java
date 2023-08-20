package com.ecommerce.springbootecommerce.entity;


import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "PRODUCT_ITEM")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemEntity extends BaseEntity{
    private String productId;

    private long stock;

    private long sold;

    private String image;

    private double price;

    private String status;

    private String variationName;
    private String variationValue;

    @DBRef
    private List<OrderItemEntity> orderItems;

    @DBRef
    private List<CartItemEntity> cartItems;
}
