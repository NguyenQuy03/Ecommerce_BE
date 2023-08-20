package com.ecommerce.springbootecommerce.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity{
    private String accountId;

    private String categoryId;

    private String name;

    private String image;

    private String description;

    private String status;

    private List<Map<String, String>> specification;

    private List<ProductItemEntity> productItems;
}
