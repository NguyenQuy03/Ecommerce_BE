package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // FIND-ONE
    Optional<ProductEntity> findOneByIdAndAccountId(long id, long accountId);

    // FIND ALL
    Slice<ProductEntity> findAllByStatus(String status, Pageable pageable);
    Slice<ProductEntity> findAllByCategoryId(long categoryId, Pageable pageable);
    Slice<ProductEntity> findAllByNameContains(String keyword, Pageable pageable);

    Page<ProductEntity> findAllByAccountIdAndStatus(long accountId, String status, Pageable pageable);

    @Query(
        value = "SELECT DISTINCT p.* FROM product as p"+
                " inner join product_item as pitem on pitem.product_id = p.id" +
                " where p.account_id = :accountId and p.status = :productStatus and pitem.status = :productItemStatus",
        countQuery = "SELECT count(DISTINCT p.id) FROM product as p"+
                " inner join product_item as pitem on pitem.product_id = p.id" +
                " where p.account_id = :accountId and p.status = :productStatus and pitem.status = :productItemStatus",
        nativeQuery = true)
    Page<ProductEntity> findAllByAccountIdAndProductStatusAndProductItemStatus(
        @Param(value = "accountId") long accountId, @Param(value = "productStatus") String productStatus,
        @Param(value = "productItemStatus") String productItemStatus, Pageable pageable
    );

    @Query(
        value = "SELECT product.id, SUM(sold) as totalSold from product inner join product_item on product.id = product_item.product_id" +
                " WHERE product.account_id = 2 GROUP BY product.id ORDER BY totalSold DESC",
        countQuery = "SELECT COUNT(*) FROM (SELECT product.id FROM product inner join product_item on product.id = product_item.product_id"+
                " WHERE product.account_id = 2 GROUP BY product.id) as totalItem",
        nativeQuery = true
    )
    Page<ProductEntity> findTopSelling(
        Pageable pageable
    );
    
    // COUNT
    long countAllByStatus(String status);
    long countAllByCategoryId(long categoryId);
    long countByNameContains(String keyword);

}
