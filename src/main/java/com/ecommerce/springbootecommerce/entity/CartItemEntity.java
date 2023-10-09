package com.ecommerce.springbootecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity extends BaseEntity{
    private long quantity;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_item_id", referencedColumnName = "id")
    private ProductItemEntity productItem;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = true)
    private CartEntity cart;

    public void increQuantity(long newQuantity) {
        this.quantity += newQuantity;
    }
}