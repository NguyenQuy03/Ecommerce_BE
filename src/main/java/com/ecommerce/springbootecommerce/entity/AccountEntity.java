package com.ecommerce.springbootecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity extends BaseEntity {

    @NotNull
    @Column(columnDefinition = "nvarchar(100)")
    private String username;
    
    @NotNull
    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;
    
    @NotNull
    @Email
    private String email;
    
    @NotNull
    private String password;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String address;
    
    private String phoneNumber;
    
    @NotNull
    private boolean status;
}
