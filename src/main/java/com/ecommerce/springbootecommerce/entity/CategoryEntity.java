package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Category")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity{

    @NotNull
    private String code;
    
    @NotNull
    private String thumbnail;
}
