package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    ProductEntity findOneById(Long id);
    
    Page<ProductEntity> findByStockGreaterThan(Integer stock, Pageable page);
    long countByStockGreaterThan(Integer stock);
    
    Page<ProductEntity> findByStockEquals(Integer stock, Pageable page);
    long countByStockEquals(Integer stock);

    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByAccountId(long accountId, Pageable pageable);
    Slice<ProductEntity> findAllByCategoryId(long categoryId, Pageable pageable);
    
    long countAllByAccountId(long accountId);
    long countAllByCategoryId(Long categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);

}
