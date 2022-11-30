package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findRoleByName(String name);
}
