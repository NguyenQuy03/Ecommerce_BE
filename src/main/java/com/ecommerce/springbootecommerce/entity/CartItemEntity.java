package com.ecommerce.springbootecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PRODUCT_ITEM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity extends BaseEntity{
    private String cartId;

    @DBRef
    private ProductEntity product;

    private long quantity;
}