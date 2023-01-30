package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
    Optional<CartEntity> findByStatus(String status);
    Optional<CartEntity> findByStatusAndAccountId(String status, long id);
    
    CartEntity findOneByStatusAndAccountId(String status, Long id);
}
