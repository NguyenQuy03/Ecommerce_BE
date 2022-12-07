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
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(true);
        return entity;
    }
    
    public AccountEntity toInfoEntity(AccountDTO dto, AccountEntity entity) {
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());
        return entity;
    }
    
    public AccountEntity toPasswordEntity(AccountDTO dto, AccountEntity entity) {
        entity.setPassword(dto.getPassword());
        return entity;
    }
    
    public AccountDTO toDTO(AccountEntity entity) {
        AccountDTO dto = new AccountDTO();    
        
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setBalance(entity.getBalance());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        dto.setPhoneNumber(entity.getPhoneNumber());
        
        return dto;
    }
    
    
}
