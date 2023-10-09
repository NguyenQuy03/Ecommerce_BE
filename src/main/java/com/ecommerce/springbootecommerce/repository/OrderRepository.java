package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOneByAccountIdAndStatus(Long accountId, String status);

    Page<OrderEntity> findAllByAccountIdAndStatus(Long accountId, String status, Pageable page);

    List<OrderEntity> findAllByStatus(String status);

    long countByAccountIdAndStatus(Long accountId, String status);
}
