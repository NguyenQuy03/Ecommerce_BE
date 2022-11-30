package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;

public interface IAccountService {
    AccountEntity findAccountByUserName(String userName);
    boolean accountExist(String email);
    void register(AccountDTO accountDTO);
    List<String> findAllUserName();
    List<String> findAllEmail();
}
