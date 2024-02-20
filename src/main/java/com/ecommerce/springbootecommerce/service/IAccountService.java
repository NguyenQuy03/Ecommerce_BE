package com.ecommerce.springbootecommerce.service;

import javax.servlet.http.HttpServletRequest;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.response.AuthResponse;
import com.ecommerce.springbootecommerce.dto.AccountDTO;

public interface IAccountService {
    AccountDTO findByUsername(String username);

    AccountDTO findById(Long id);

    boolean isAccountExistByEmail(String email);

    boolean isAccountExistByUsername(String username);

    void register(RegisterRequest request);

    void registerSeller(AccountDTO accountDTO);

    void update(AccountDTO accountDTO);

    AuthResponse authenticate(LogInRequest request, HttpServletRequest httpServletRequest);

}
