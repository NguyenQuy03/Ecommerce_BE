package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.RoleDTO;

public interface IRoleService {
    List<RoleDTO> findAll();

    RoleDTO findOneByCode(String roleCode);
}
