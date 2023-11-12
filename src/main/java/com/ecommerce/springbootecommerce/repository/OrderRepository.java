package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findOneByAccountIdAndStatus(Long accountId, OrderStatus status);

    Page<OrderEntity> findAllByAccountIdAndStatus(Long accountId, OrderStatus status, Pageable page);

    List<OrderEntity> findAllByStatus(OrderStatus status);

    List<OrderEntity> findAllByCartIdAndStatus(Long cartId, OrderStatus status);
    
    long countByAccountIdAndStatus(Long accountId, OrderStatus status);

    List<OrderEntity> findAllByCartId(Long cartId);

    Optional<OrderEntity> findOneByCartIdAndAccountId(long cartId, long accountId);

    Optional<OrderEntity> findOneByCartIdAndAccountIdAndStatus(Long cartId, Long accountId, OrderStatus status);
}
