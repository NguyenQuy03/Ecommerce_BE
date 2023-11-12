package com.ecommerce.springbootecommerce.util.converter.account_role;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;

@Component
public class AccountRoleConverter {
    @Autowired
    private ModelMapper mapper;

    public AccountRoleDTO toDTO(AccountRoleEntity entity) {
        return mapper.map(entity, AccountRoleDTO.class);
    }

    public List<AccountRoleDTO> toDTO(List<AccountRoleEntity> accountRoleEntities) {
        List<AccountRoleDTO> dtos = new ArrayList<>();
        for (AccountRoleEntity entity : accountRoleEntities) {
            AccountRoleDTO dto = toDTO(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    public AccountRoleEntity toEntity(AccountRoleDTO dto) {
        return mapper.map(dto, AccountRoleEntity.class);
    }
}
