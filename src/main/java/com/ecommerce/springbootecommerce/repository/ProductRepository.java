package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    //FINDONE
    ProductEntity findOneById(Long id);
    
    //FINDALL
    Slice<ProductEntity> findByStockGreaterThanAndAccountIdAndStatusNot(long stock, Long accountId, String ignoreStatus, Pageable page);
    Slice<ProductEntity> findByStockEqualsAndAccountIdAndStatusNot(long stock, Long accountId, String ignoreStatus, Pageable page);
    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByAccountId(long accountId, Pageable pageable);
    Slice<ProductEntity> findAllByAccountIdAndStatus(Long id, String status);
    Slice<ProductEntity> findAllByCategoryId(long categoryId, Pageable pageable);
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);
    Slice<ProductEntity> findAllByCreatedByOrderBySoldDesc(String sellerName);
    Slice<ProductEntity> findAllByAccountIdAndStatusNot(Long id, String ignoreStatus, Pageable pageable);
    
    
    //COUNT
    long countAllByAccountId(long accountId);
    long countByStockGreaterThanAndAccountIdAndStatusNot(long stock, Long accountId, String ignoreStatus);
    long countByStockEqualsAndAccountIdAndStatusNot(long stock, Long accountId, String ignoreStatus);
    long countAllByCategoryId(Long categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(long accountId, String status);
    long countAllByAccountIdAndStatusNot(Long id, String ignoreStatus);
    
    
    //CUSTOMIZE
    @Transactional
    @Modifying
    @Query(value="UPDATE product as p SET p.status = \"REMOVED\" WHERE p.id = ?1", nativeQuery = true)
    void softDelete(long id);

    @Transactional
    @Modifying
    @Query(value="UPDATE product as p SET p.status = \"ACTIVE\" WHERE p.id = ?1", nativeQuery = true)
    void restore(long id);
    
}
