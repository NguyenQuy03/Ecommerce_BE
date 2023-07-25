package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.dto.AccountDTO;

import javax.servlet.http.HttpServletRequest;

public interface IAccountService {
    AccountDTO findByUsername(String username);
    AccountDTO findOneById(String id);
    boolean isAccountExistByEmail(String email);
    boolean isAccountExistByUsername(String username);
    void register(RegisterRequest request);
    void registerSeller(AccountDTO accountDTO);
    void update(AccountDTO accountDTO);
    String authenticate(LogInRequest request, HttpServletRequest httpServletRequest);

}
