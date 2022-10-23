package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class CategoryEntity extends BaseEntity{
    
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;
}
