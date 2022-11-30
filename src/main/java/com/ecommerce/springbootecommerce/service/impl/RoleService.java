package com.ecommerce.springbootecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IRoleService;

@Service
public class RoleService implements IRoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity findRoleByName(String name) {
        RoleEntity roleEntity = roleRepository.findRoleByName(name);
        return roleEntity;
    }

}
