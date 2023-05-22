package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

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
    Optional<ProductEntity> findByAccountIdAndId(long accountId, long id);
    
    //FINDALL
    Slice<ProductEntity> findByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2, Pageable page);
    Slice<ProductEntity> findByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2, Pageable page);
    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByAccountId(long accountId, Pageable pageable);
    Slice<ProductEntity> findAllByAccountIdAndStatus(Long id, String status);
    Slice<ProductEntity> findAllByCategoryId(long categoryId, Pageable pageable);
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);
    Slice<ProductEntity> findAllByCreatedByOrderBySoldDesc(String sellerName);
    Slice<ProductEntity> findAllByAccountIdAndStatusNotAndStatusNot(Long id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    
    
    //COUNT
    long countAllByAccountId(long accountId);
    long countByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2);
    long countByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2);
    long countAllByCategoryId(Long categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(long accountId, String status);
    long countAllByAccountIdAndStatusNotAndStatusNot(Long id, String ignoreStatus1, String ignoreStatus2);

    //EXIST
    Optional<ProductEntity> findOneByIdAndStatusNot(Long id, String ignoreStatus);
    
    //CUSTOMIZE
    @Transactional
    @Modifying
    @Query(value="UPDATE product as p SET p.status = ?1 WHERE p.id = ?2", nativeQuery = true)
    void softDelete(String status, long id);

    @Transactional
    @Modifying
    @Query(value="UPDATE product as p SET p.status = \"ACTIVE\" WHERE p.id = ?1", nativeQuery = true)
    void restore(long id);

    
}
