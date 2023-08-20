package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends MongoRepository<ProductItemEntity, String> {

    Slice<ProductItemEntity> findAllByStatus(String status, Pageable pageable);
}
