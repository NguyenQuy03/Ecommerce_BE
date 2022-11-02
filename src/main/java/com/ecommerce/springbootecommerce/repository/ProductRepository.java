package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    ProductEntity findOneById(Long id);
}
