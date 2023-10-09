package com.ecommerce.springbootecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity extends BaseEntity {

    @NotNull
    private long quantity;
    
    @NotNull
    private double curPrice;

    @NotNull
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = true)
    private OrderEntity orders;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_item_id", referencedColumnName = "id")
    private ProductItemEntity productItem;
}
