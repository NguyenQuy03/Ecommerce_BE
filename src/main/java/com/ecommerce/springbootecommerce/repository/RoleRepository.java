package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleEntity, String> {
    Optional<RoleEntity> findOneByCode(String code);
}
