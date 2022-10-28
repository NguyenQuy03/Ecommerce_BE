package com.ecommerce.springbootecommerce.entity.Account;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "buyer")
@Getter
@Setter
@AllArgsConstructor
public class BuyerEntity extends AccountEntity{
    
    @Column(name = "balance")
    private Double balance;
    
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<OrderEntity> orders;
    
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<DeliveryEntity> deliveries;
}
