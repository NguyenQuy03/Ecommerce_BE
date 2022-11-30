package com.ecommerce.springbootecommerce.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;

@Component
public class AccountConverter {
    
    
    
    public AccountEntity toEntity(AccountDTO dto) {
        AccountEntity entity = new AccountEntity();    
        
        entity.setAddress(dto.getAddress());
        entity.setBalance(dto.getBalance());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setUserName(dto.getUserName());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(true);
        return entity;
    }
}
