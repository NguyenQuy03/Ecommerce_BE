package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.util.converter.account_role.AccountRoleConverter;

@Service
public class AccountRoleService implements IAccountRoleService {

    private final AccountRoleRepository accountRoleRepo;

    private final AccountRoleConverter accountRoleConverter;

    public AccountRoleService(AccountRoleRepository accountRoleRepo, AccountRoleConverter accountRoleConverter) {
        this.accountRoleRepo = accountRoleRepo;
        this.accountRoleConverter = accountRoleConverter;
    }

    @Override
    public List<AccountRoleDTO> findAllByRoleCodeIn(Set<String> roleCodes) {
        List<AccountRoleEntity> accountRoleEntities = accountRoleRepo.findAllByRoleCodeIn(roleCodes);
        return accountRoleConverter.toDTO(accountRoleEntities);
    }

    @Override
    public void save(AccountRoleDTO accountRoleDTO) {
        AccountRoleEntity accountRoleEntity = accountRoleConverter.toEntity(accountRoleDTO);
        accountRoleRepo.save(accountRoleEntity);
    }

}
