package com.ecommerce.springbootecommerce.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.util.converter.AccountRoleConverter;

@Service
public class AccountRoleService implements IAccountRoleService {

    @Autowired
    private AccountRoleRepository accountRoleRepo;

    @Autowired
    private AccountRoleConverter accountRoleConverter;

    @Override
    public Set<AccountRoleDTO> findAllByRole(String roleCode) {
        Set<AccountRoleEntity> accountRoleEntities = accountRoleRepo.findAllByRoleCode(roleCode);
        return accountRoleConverter.toDTO(accountRoleEntities);
    }

    @Override
    public void save(AccountRoleDTO accountRoleDTO) {
        AccountRoleEntity accountRoleEntity = accountRoleConverter.toEntity(accountRoleDTO);
        accountRoleRepo.save(accountRoleEntity);
        
    }
    
}
