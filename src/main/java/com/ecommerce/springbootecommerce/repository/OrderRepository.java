package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    OrderEntity findOneByProductIdAndAccountIdAndStatus(Long productId, Long accountId, String status);
    Optional<OrderEntity> findByProductIdAndAccountIdAndStatus(Long productID, Long accountId, String status);
    Long countByAccountId(Long id);
}
