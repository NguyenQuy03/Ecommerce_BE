package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.entity.RoleEntity;

public interface IRoleService {
    RoleEntity findRoleByName(String name);
}
