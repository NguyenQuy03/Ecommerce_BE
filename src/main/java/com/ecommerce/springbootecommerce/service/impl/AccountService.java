package com.ecommerce.springbootecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.config.AuthenticationConfig;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Service
public class AccountService implements IAccountService{
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountConverter accountConverter;
    
    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private AccountRoleRepository accountRoleRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AccountDTO findByUsername(String username) {
        if(accountRepository.findByUsername(username).isPresent()) {
            AccountEntity account = accountRepository.findByUsername(username).get();
            return accountConverter.toDTO(account);
        } else {
            return null;
        }
    }

    @Override
    public boolean isAccountExistByEmail(String email) {      
        return accountRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isAccountExistByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
    
    @Override
    public void register(AccountDTO accountDTO) {
        AccountEntity accountEntity = new AccountEntity();
        
        if (accountDTO.getId() != null) {
            
            AccountEntity preAccountEntity = accountRepository.findById(accountDTO.getId()).get();
            
            if (preAccountEntity.getPassword().equals(accountDTO.getPassword())) {
                accountEntity = accountConverter.toInfoEntity(accountDTO, preAccountEntity);
            } else {
                accountEntity = accountConverter.toPasswordEntity(accountDTO, preAccountEntity);
            }
        } else {
            
            String passwordEncode = authenticationConfig.passwordEncoder().encode(accountDTO.getPassword());
            accountEntity = accountConverter.toEntity(accountDTO);
            accountEntity.setPassword(passwordEncode);
        }
        
        AccountEntity returnedEntity = accountRepository.save(accountEntity);
        
        if (accountDTO.getId() == null) {
            RoleEntity roleEntity = roleRepository.findOneByCode(SystemConstant.ROLE_BUYER);
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setAccount(returnedEntity);
            accountRoleEntity.setRole(roleEntity);
            accountRoleRepository.save(accountRoleEntity);
        }

    }

    @Override
    public AccountDTO findOneById(long id) {
        if(accountRepository.findById(id).isPresent())
            return accountConverter.toDTO(accountRepository.findById(id).get());
        else
            return null;
    }

}
