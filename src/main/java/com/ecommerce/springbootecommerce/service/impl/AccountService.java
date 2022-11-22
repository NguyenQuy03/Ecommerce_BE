package com.ecommerce.springbootecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Service
public class AccountService implements IAccountService{
    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountEntity findUserByUserName(String userName) {
        AccountEntity account = accountRepository.findOneByUserName(userName);
        return account;
    }

    @Override
    public boolean accountExist(String email) {      
        return accountRepository.findByEmail(email).isPresent();
    }


}
