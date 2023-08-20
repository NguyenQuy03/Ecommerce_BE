package com.ecommerce.springbootecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@Document(collection = "ACCOUNT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {
    private String username;

    private String fullName;
    
    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    private boolean status;

    private Set<String> roleCodes;

    private String cartId;

    @ReadOnlyProperty
    @DocumentReference(lazy = true)
    private Set<OrderEntity> orders;

    // SELLER
    @ReadOnlyProperty
    @DocumentReference(lazy = true)
    private Set<ProductEntity> products;
}
