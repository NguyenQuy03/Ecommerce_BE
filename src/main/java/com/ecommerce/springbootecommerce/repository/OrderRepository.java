package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    Optional<OrderEntity> findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Long countByCartIdAndStatus(Long cartId, String status);
    OrderEntity findOneById(Long id);
        
    Slice<OrderEntity> findAllByCartIdAndStatus(Long cartId, String status, Pageable page);
    List<OrderEntity> findAllByCartIdAndStatus(Long cartId, String status);
    
    Slice<OrderEntity> findAllByStatus(String status, Pageable pageable);
}
