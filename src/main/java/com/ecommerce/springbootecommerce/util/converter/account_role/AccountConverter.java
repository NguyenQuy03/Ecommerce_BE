package com.ecommerce.springbootecommerce.util.converter.account_role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;

@Component
public class AccountConverter {
    @Autowired
    private ModelMapper modelMapper;

    
    public AccountEntity toEntity(AccountDTO dto) {
        return modelMapper.map(dto, AccountEntity.class);
    }


    public AccountDTO toDTO(AccountEntity entity) {
        return modelMapper.map(entity, AccountDTO.class);
    }
}