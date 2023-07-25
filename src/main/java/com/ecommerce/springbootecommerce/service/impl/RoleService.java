package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.RoleRepository;
import com.ecommerce.springbootecommerce.service.IRoleService;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO findOneByCode(String roleCode) {
        Optional<RoleEntity> role = roleRepository.findOneByCode(roleCode);
        if(role.isPresent()) {
            return modelMapper.map(role, RoleDTO.class);
        }

        return null;
    }
}
