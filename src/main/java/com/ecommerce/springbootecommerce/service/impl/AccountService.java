package com.ecommerce.springbootecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.config.EncoderConfig;
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
    private EncoderConfig encoderConfig;

    @Autowired
    private AccountRoleRepository accountRoleRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AccountDTO findByUserName(String userName) {
        AccountEntity account = accountRepository.findOneByUserName(userName);
        return accountConverter.toDTO(account);
    }

    @Override
    public boolean isAccountExistByEmail(String email) {      
        return accountRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isAccountExistByUserName(String userName) {      
        return accountRepository.findByUserName(userName).isPresent();
    }
    
    @Override
    public void register(AccountDTO accountDTO) {
        AccountEntity accountEntity = new AccountEntity();
        
        if (accountDTO.getId() != null) {
            
            AccountEntity preAccountEntity = accountRepository.findOneById(accountDTO.getId());
            
            if (preAccountEntity.getPassword().equals(accountDTO.getPassword())) {
                accountEntity = accountConverter.toInfoEntity(accountDTO, preAccountEntity);
            } else {
                accountEntity = accountConverter.toPasswordEntity(accountDTO, preAccountEntity);
            }
        } else {
            
            String passwordEncode = encoderConfig.passwordEncoder().encode(accountDTO.getPassword());
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
        AccountDTO accountDTO = accountConverter.toDTO(accountRepository.findOneById(id));
        return accountDTO;
    }

}
