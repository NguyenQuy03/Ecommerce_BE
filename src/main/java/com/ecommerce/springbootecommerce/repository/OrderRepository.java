package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    Optional<OrderEntity> findOneByProductIdAndCartIdAndStatus(String productId, String cartId, String status);
    Long countByCartIdAndStatus(String cartId, String status);
    Optional<OrderEntity> findOneById(String id);
        
    Slice<OrderEntity> findAllByCartIdAndStatus(String cartId, String status, Pageable page);
    List<OrderEntity> findAllByCartIdAndStatus(String cartId, String status);
    
    Slice<OrderEntity> findAllByStatus(String status, Pageable pageable);

}
