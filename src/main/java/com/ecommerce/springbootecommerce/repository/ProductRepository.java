package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    ProductEntity findOneById(Long id);
    
    @Query(value = "SELECT * FROM product WHERE stock > 0 ", nativeQuery = true)
    Page<ProductEntity> findLiveProduct(Pageable page);
    @Query(value = "SELECT COUNT(*) FROM product WHERE stock > 0;", nativeQuery = true)
    long countLiveProduct();
    
    @Query(value = "SELECT * FROM product WHERE stock = 0 ", nativeQuery = true)
    Page<ProductEntity> findSoldOutProduct(Pageable page);
    @Query(value = "SELECT COUNT(*) FROM product WHERE stock = 0;", nativeQuery = true)
    long countSoldOutProduct();

    Slice<ProductEntity> findAllByAccountId(long accountId, Pageable pageable);

    long countAllByAccountId(long accountId);

}
