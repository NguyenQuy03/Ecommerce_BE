package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.springbootecommerce.constant.enums.product.CurrencyType;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;

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
public class ProductItemEntity extends BaseEntity {

    @NotNull
    private long stock;

    @NotNull
    private long sold;

    @NotNull
    private String image;

    @NotNull
    private String price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @NotNull
    @Column(columnDefinition = "varchar(20) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "nvarchar(max)")
    private String variation;

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
