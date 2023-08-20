package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItemEntity, String> {
    Optional<OrderItemEntity> findOneByProductItemId(String productItemId);

    Optional<OrderItemEntity> findOneById(String orderItemId);
}
