package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final AccountRoleRepository accountRoleRepo;

    public CustomUserDetailsService(AccountRepository accountRepository, AccountRoleRepository accountRoleRepo) {
        this.accountRepository = accountRepository;
        this.accountRoleRepo = accountRoleRepo;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        if (accountRepository.findByUsernameAndIsActive(username, StatusConstant.BOOLEAN_ACTIVE_STATUS).isPresent()) {
            AccountEntity account = accountRepository
                    .findByUsernameAndIsActive(username, StatusConstant.BOOLEAN_ACTIVE_STATUS).get();
            List<AccountRoleEntity> accountRoles = accountRoleRepo.findAllByAccountId(account.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (AccountRoleEntity entity : accountRoles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getRole().getCode()));
            }

            return new CustomUserDetails(
                    account.getId(),
                    account.getUsername(),
                    account.getFullName(),
                    account.getPassword(),
                    authorities);
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
