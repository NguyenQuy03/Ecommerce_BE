package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.MyAccount;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if(accountRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS).isPresent()){
            AccountEntity account = accountRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS).get();
            Set<String> accountRoles = account.getRoleCodes();
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (String role : accountRoles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            MyAccount myAccount = new MyAccount(
                    account.getUsername(),
                    account.getFullName(),
                    account.getPassword(),
                    authorities
            );

            return myAccount;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }  
}
