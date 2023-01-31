package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    OrderEntity findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Optional<OrderEntity> findByProductIdAndCartIdAndStatus(Long productID, Long cartId, String status);
    Long countByCartIdAndStatus(Long cartId, String status);
    List<OrderEntity> findAllByCartIdAndStatus(Long cartId, String status);
    OrderEntity findOneById(Long id);
    
    
}
