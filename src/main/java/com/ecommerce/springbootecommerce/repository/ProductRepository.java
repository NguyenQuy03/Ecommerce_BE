package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    ProductEntity findOneById(Long id);
    
    Slice<ProductEntity> findByStockGreaterThanAndAccountId(long stock, Long accountId, Pageable page);
    long countByStockGreaterThanAndAccountId(long stock, Long accountId);
    
    Slice<ProductEntity> findByStockEqualsAndAccountId(long stock, Long accountId, Pageable page);
    long countByStockEqualsAndAccountId(long stock, Long accountId);

    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByAccountId(long accountId, Pageable pageable);
    Slice<ProductEntity> findAllByCategoryId(long categoryId, Pageable pageable);
    
    long countAllByAccountId(long accountId);
    long countAllByCategoryId(Long categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);

    Slice<ProductEntity> findAllByCreatedByOrderBySoldDesc(String sellerName);

}
