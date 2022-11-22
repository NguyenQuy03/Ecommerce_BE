package com.ecommerce.springbootecommerce.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyAccount extends User {
    private String fullName;

    public MyAccount(String username, String fullName, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fullName = fullName;
    }
}
