package com.ecommerce.springbootecommerce.entity;

import java.util.Set;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "CATEGORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity{

    private String code;

    private String thumbnail;

    @ReadOnlyProperty
    @DocumentReference(lazy = true)
    private Set<ProductEntity> products;

}
