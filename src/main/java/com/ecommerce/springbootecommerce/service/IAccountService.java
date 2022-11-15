package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.entity.AccountEntity;

public interface IAccountService {
    AccountEntity findUserByUserName(String userName);
}
