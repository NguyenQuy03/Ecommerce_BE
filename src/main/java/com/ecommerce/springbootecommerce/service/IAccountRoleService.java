package com.ecommerce.springbootecommerce.service;

import java.util.Set;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;

public interface IAccountRoleService {
    Set<AccountRoleDTO> findAllByRole(String roleCode);
    void save(AccountRoleDTO accountRoleDTO);
}
