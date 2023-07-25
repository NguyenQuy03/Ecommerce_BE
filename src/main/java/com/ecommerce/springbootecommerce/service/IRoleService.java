package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.RoleDTO;
import com.ecommerce.springbootecommerce.entity.RoleEntity;

public interface IRoleService {

    RoleDTO findOneByCode(String roleCode);
}
