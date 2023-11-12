package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;

public interface IAccountRoleService {
    List<AccountRoleDTO> findAllByRole(String roleCode);
    void save(AccountRoleDTO accountRoleDTO);
}
