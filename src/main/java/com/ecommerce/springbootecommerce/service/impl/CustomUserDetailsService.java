package com.ecommerce.springbootecommerce.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.MyAccount;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountEntity account = accountRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS).get();
        Set<AccountRoleEntity> accountRoles = accountRoleRepository.findAllByAccountId(account.getId());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (AccountRoleEntity entity : accountRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getRole().getCode()));
        }
        MyAccount myAccount = new MyAccount(
                account.getUsername(),
                account.getFullName(),
                account.getPassword(),
                authorities
        );

        return (UserDetails) myAccount;
    }  
}
