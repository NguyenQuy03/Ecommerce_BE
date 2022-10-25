package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.MyAccount;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntiry = accountRepository.findOneByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);

        if (accountEntiry == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role: accountEntiry.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        MyAccount myAccount = new MyAccount(accountEntiry.getUserName(), accountEntiry.getPassword(), true, true, true, true, null);
        myAccount.setFullName(accountEntiry.getFullName());
        
        return myAccount;
    }
    
}
