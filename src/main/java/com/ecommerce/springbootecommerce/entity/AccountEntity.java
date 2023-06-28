package com.ecommerce.springbootecommerce.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "fullname")
    private String fullName;
    
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private boolean status;

    // BUYER
    @OneToMany(mappedBy = "account")
    private Set<CartEntity> carts;

    // SELLER
    @OneToMany(mappedBy = "account")
    private Set<ProductEntity> products;

}
