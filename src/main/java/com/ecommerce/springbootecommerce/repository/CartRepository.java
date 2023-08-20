package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findOneById(String id);

    Optional<CartEntity> findOneByAccountId(String accountId);
}
