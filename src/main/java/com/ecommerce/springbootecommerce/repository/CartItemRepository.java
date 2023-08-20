package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.CartItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItemEntity, String> {
    List<CartItemEntity> findAllByCartId(String cartId);
}
