package com.ecommerce.springbootecommerce.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;

@Component
public class AccountRoleConverter {
    public AccountRoleDTO toDTO(AccountRoleEntity entity) {
        AccountRoleDTO dto = new AccountRoleDTO();
        dto.setAccountId(entity.getAccount().getId());
        dto.setRoleCode(entity.getRole().getCode());
        
        return dto;
    }

    public Set<AccountRoleDTO> toDTO(Set<AccountRoleEntity> accountRoleEntities) {
        Set<AccountRoleDTO> dtos = new HashSet<>();
        for (AccountRoleEntity entity : accountRoleEntities) {
            AccountRoleDTO dto = toDTO(entity);
            dtos.add(dto);
        }
        return dtos;
    }
}
