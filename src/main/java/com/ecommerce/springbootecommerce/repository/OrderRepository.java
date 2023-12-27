package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOneByBuyerIdAndSellerIdAndStatus(Long buyerId, Long sellerId, OrderStatus status);

    List<OrderEntity> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status);

    List<OrderEntity> findAllByStatus(OrderStatus status);

    Page<OrderEntity> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status, Pageable page);

    long countByBuyerIdAndStatus(Long buyerId, OrderStatus status);
}
