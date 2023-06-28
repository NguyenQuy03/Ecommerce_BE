package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.AccountDTO;

public interface IAccountService {
    AccountDTO findByUsername(String username);
    AccountDTO findOneById(long id);
    boolean isAccountExistByEmail(String email);
    boolean isAccountExistByUsername(String username);
    void register(AccountDTO accountDTO);
    
}
