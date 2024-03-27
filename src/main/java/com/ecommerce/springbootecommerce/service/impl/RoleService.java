package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.RoleDTO;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.RoleRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.service.IRoleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO findOneByCode(String roleCode) {
        Optional<RoleEntity> role = roleRepository.findOneByCode(roleCode);
        if (role.isPresent()) {
            return modelMapper.map(role, RoleDTO.class);
        }

        return null;
    }

    @Override
    public List<RoleDTO> findAll() {
        List<RoleEntity> roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }
}
