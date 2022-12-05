package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.AccountDTO;

public interface IAccountService {
    AccountDTO findAccountByUserName(String userName);
    AccountDTO findOneById(long id);
    boolean accountExist(String email);
    void register(AccountDTO accountDTO);
    List<String> findAllUserName();
    List<String> findAllEmail();
}
