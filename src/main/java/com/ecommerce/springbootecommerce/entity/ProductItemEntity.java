package com.ecommerce.springbootecommerce.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemEntity extends BaseEntity{
    
    @NotNull
    private long stock;
    
    @NotNull
    private long sold;
    
    @NotNull
    private String image;
    
    @NotNull
    private double price;
    
    @NotNull
    @Column(columnDefinition = "varchar(50) default 'ACTIVE'")
    private String status;
    
    private String variationName;
    private String variationValue;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
    
    public void increateSold(long sold) {
        this.sold += sold;
    }

    public void decreaseStock(long stock) {
        this.stock -= stock;
    }
}
