package com.ecommerce.springbootecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;

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
    @Column(columnDefinition = "varchar(20)", name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orders_id", nullable = false)
    private OrderEntity orders;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_item_id", referencedColumnName = "id")
    private ProductItemEntity productItem;
}
