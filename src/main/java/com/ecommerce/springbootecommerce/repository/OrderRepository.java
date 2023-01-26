package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    OrderEntity findOneByProductIdAndAccountIdAndStatus(Long productId, Long accountId, String status);
    Optional<OrderEntity> findByProductIdAndAccountIdAndStatus(Long productID, Long accountId, String status);
    Long countByAccountId(Long id);
    List<OrderEntity> findAllByAccountId(Long accountId);
    
    
}
