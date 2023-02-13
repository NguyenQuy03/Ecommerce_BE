package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
    Optional<OrderEntity> findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Long countByCartIdAndStatus(Long cartId, String status);
    OrderEntity findOneById(Long id);
        
    Slice<OrderEntity> findAllByCartIdAndStatus(Long cartId, String status, Pageable page);
    List<OrderEntity> findAllByCartIdAndStatus(Long cartId, String status);
    
    Slice<OrderEntity> findAllByStatus(String status, Pageable pageable);
    
    @Modifying 
    @Query(value = "SELECT o.* FROM product as p inner join orders as o on o.product_id = p.id WHERE o.status = :status and p.created_by = :name ;", nativeQuery = true)
    Slice<OrderEntity> findAllByStatusAndSellerName(@Param("status") String status, @Param("name") String sellerName);
}
