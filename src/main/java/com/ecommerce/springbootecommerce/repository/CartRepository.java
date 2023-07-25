package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findOneById(String id);
    
    Optional<CartEntity> findOneByStatusAndAccountId(String status, String id);

    Optional<CartEntity> findByStatusAndAccountUsername(String stringActiveStatus, String username);

}
