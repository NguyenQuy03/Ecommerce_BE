package com.ecommerce.springbootecommerce.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
public class RoleEntity extends BaseEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<AccountEntity> accounts = new HashSet<>();
}
