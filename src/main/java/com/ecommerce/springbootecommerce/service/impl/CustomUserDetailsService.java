package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.MyAccount;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntiry = accountRepository.findOneByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);

        if (accountEntiry == null) {
            System.out.print("User not found in the database");
            return null;
        } else {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            accountEntiry.getRoles().forEach(role ->{
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
            });    
            
            MyAccount myAccount = new MyAccount(accountEntiry.getUserName(), accountEntiry.getFullName(), accountEntiry.getPassword() , authorities);
            return (UserDetails) myAccount;
        }  
    }
    
}
