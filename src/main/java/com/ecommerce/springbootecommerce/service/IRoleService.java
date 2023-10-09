package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.RoleDTO;

public interface IRoleService {

    RoleDTO findOneByCode(String roleCode);
}
