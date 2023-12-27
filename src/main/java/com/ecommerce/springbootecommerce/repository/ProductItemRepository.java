package com.ecommerce.springbootecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.ProductItemEntity;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItemEntity, Long> {    
    
    // @Query(value = "SELECT * FROM PRODUCT_ITEM WHERE product_id = ?", nativeQuery = true)
    List<ProductItemEntity> findAllByProductId(Long productId);
    Slice<ProductItemEntity> findAllByStatus(String status, Pageable pageable);


    List<ProductItemEntity> findAllByCreatedByOrderBySoldDesc(String username);
}
