package com.ecommerce.springbootecommerce.service;

import java.util.List;
import java.util.Set;

import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;

public interface IAccountRoleService {
    List<AccountRoleDTO> findAllByRoleCodeIn(Set<String> roleCodes);

    void save(AccountRoleDTO accountRoleDTO);
}
