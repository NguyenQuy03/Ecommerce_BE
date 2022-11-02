package com.ecommerce.springbootecommerce.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {

    @Column(name = "username")
    private String userName;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "status")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles", joinColumns = {
            @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false) })
    private Set<RoleEntity> roles = new HashSet<>();

    // BUYER
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<OrderEntity> orders;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeliveryEntity> deliveries;

    // SELLER
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductEntity> products;
}
