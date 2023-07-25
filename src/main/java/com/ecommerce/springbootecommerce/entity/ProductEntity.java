package com.ecommerce.springbootecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductEntity extends BaseEntity{
    private String name;

    private String brand;
    
    private double price;

    private Binary image;

    private String description;
    
    private String details;
    
    private long stock;
    
    private String status;

    private long sold;

    private List<Map<String, String>> specifications;

    private String accountId;

    private String categoryId;
}
