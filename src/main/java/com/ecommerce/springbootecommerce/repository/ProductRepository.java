package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    // FIND-ONE
    Optional<ProductEntity> findOneById(String id);
    Optional<ProductEntity> findByAccountIdAndId(String accountId, String id);

    // FIND ALL
    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByCategoryId(String categoryId, Pageable pageable);
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);

    // COUNT
    long countAllByCategoryId(String categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(String accountId, String status);
}
