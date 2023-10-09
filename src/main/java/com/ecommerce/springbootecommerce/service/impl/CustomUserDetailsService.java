package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        if(accountRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS).isPresent()){
            AccountEntity account = accountRepository.findByUsernameAndStatus(username, SystemConstant.ACTIVE_STATUS).get();
            Set<AccountRoleEntity> accountRoles = accountRoleRepo.findAllByAccountId(account.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (AccountRoleEntity entity : accountRoles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getRole().getCode()));
            }
            CustomUserDetails userDetails = new CustomUserDetails(
                    account.getId(),
                    account.getUsername(),
                    account.getFullName(),
                    account.getPassword(),
                    authorities
            );

            return userDetails;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }  
}
