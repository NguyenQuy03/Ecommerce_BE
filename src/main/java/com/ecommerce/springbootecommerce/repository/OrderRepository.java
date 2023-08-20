package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    Optional<OrderEntity> findOneByAccountIdAndStatus(String accountId, String status);
    Optional<OrderEntity> findOneById(String id);

    List<OrderEntity> findAllByAccountIdAndStatus(String accountId, String status);

    List<OrderEntity> findAllByStatus(String status);

    Long countByAccountIdAndStatus(String accountId, String status);
}
