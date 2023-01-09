package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.AccountDTO;

public interface IAccountService {
    AccountDTO findAccountByUserName(String userName);
    AccountDTO findOneById(long id);
    boolean isAccountExistByEmail(String email);
    boolean isAccountExistByUserName(String userName);
    void register(AccountDTO accountDTO);
}
