package com.ecommerce.springbootecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
    @Query(value = "SELECT o.id, o.quantity, p.image, p.name, p.price FROM orders as o INNER JOIN product as p ON o.product_id=p.id;", nativeQuery = true)
    List<CartDTO> findALlOrderByNativeQuery();
}
