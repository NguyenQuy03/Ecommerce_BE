package com.ecommerce.springbootecommerce.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.converter.AccountRoleConverter;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;

@Service
public class AccountRoleService implements IAccountRoleService{
    
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    
    @Autowired
    private AccountRoleConverter accountRoleConverter;

    @Override
    public Set<AccountRoleDTO> findAllByRole(String roleCode) {
        Set<AccountRoleEntity> accountRoleEntities = accountRoleRepository.findAllByRoleCode(roleCode);
        Set<AccountRoleDTO> accountRoleDTOs = accountRoleConverter.toDTO(accountRoleEntities);
        return accountRoleDTOs;
    }

}
