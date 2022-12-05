package com.ecommerce.springbootecommerce.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.config.EncoderConfig;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
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
    private RoleService roleService;

    @Override
    public AccountDTO findAccountByUserName(String userName) {
        AccountEntity account = accountRepository.findOneByUserName(userName);
        return accountConverter.toDTO(account);
    }

    @Override
    public boolean accountExist(String email) {      
        return accountRepository.findByEmail(email).isPresent();
    }

    @Override
    public void register(AccountDTO accountDTO) {
        String passwordEncode = encoderConfig.passwordEncoder().encode(accountDTO.getPassword());
        
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity roleEntity = roleService.findRoleByName("buyer");
        roles.add(roleEntity);
        
        AccountEntity accountEntity = accountConverter.toEntity(accountDTO);
        accountEntity.setRoles(roles);
        accountEntity.setPassword(passwordEncode);
        
        accountRepository.save(accountEntity);
    }

    @Override
    public List<String> findAllUserName() {
        return accountRepository.findAllUserName();
    }

    @Override
    public List<String> findAllEmail() {
        return accountRepository.findAllEmail();
    }

    @Override
    public AccountDTO findOneById(long id) {
        AccountDTO accountDTO = accountConverter.toDTO(accountRepository.findOneById(id));
        return accountDTO;
    }


}
