package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    // FIND-ONE
    Optional<ProductEntity> findOneById(String id);
    Optional<ProductEntity> findByAccountIdAndId(String accountId, String id);

    // FIND ALL
    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByCategoryId(String categoryId, Pageable pageable);
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);

    Page<ProductEntity> findAllByAccountIdAndStatus(String accountId, String status, Pageable pageable);

    @Query(value = "{ 'accountId': ?0, 'status': { $nin: ?1 } }")
    Page<ProductEntity> findAllValid(String accountId, List<String> status, Pageable pageable);

    @Query(value = "{ 'accountId': ?0, 'status': { $nin: ?1 }, 'productItems.stock': { $gt: ?2 } }")
    Page<ProductEntity> findAllLive(
            String accountId, List<String> status,
            long stock, Pageable pageable
    );
    @Query(value = "{ 'accountId': ?0, 'status': { $nin: ?1 }, 'productItems.stock': { $eq: ?2 } }")
    Page<ProductEntity> findSoldOut(
            String accountId, List<String> status,
            long stock, Pageable pageable
    );
    
    // COUNT
    long countAllByCategoryId(String categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(String accountId, String status);
}
