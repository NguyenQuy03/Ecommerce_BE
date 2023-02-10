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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepository.findOneByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);
        Set<AccountRoleEntity> accountRoleEntities = accountRoleRepository.findAllByAccountId(accountEntity.getId());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (AccountRoleEntity accountRoleEntity : accountRoleEntities) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + accountRoleEntity.getRole().getCode()));
        }
        
        MyAccount myAccount = new MyAccount(accountEntity.getUserName(), accountEntity.getFullName(), accountEntity.getPassword(), authorities);
        return (UserDetails) myAccount;
    }
    
}
