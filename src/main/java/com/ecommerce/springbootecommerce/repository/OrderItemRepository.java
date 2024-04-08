package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    Optional<OrderItemEntity> findOneByProductItemId(Long id);

    Optional<OrderItemEntity> findOneById(Long orderItemId);

    Optional<OrderItemEntity> findOneByOrdersIdAndProductItemId(Long orderId, Long id);

    Optional<OrderItemEntity> findOneByOrdersIdAndProductItemIdAndStatus(Long orderId, Long productId,
            OrderStatus status);

    Optional<OrderItemEntity> findOneByIdAndStatus(Long id, OrderStatus status);

    List<OrderItemEntity> findAllByOrdersId(Long orderId);

    List<OrderItemEntity> findAllByOrdersIdAndStatus(Long orderId, OrderStatus status);

    // CUSTOM QUERY
    @Query(value = "SELECT * FROM order_item INNER JOIN orders ON orders.id = order_item.orders_id" +
            " INNER JOIN PRODUCT_ITEM ON order_item.product_item_id = product_item.id" +
            " where product_item.created_by = :sellerName and order_item.status = :orderStatus", countQuery = "SELECT count(1) FROM ORDERS INNER JOIN ORDER_ITEM ON ORDERS.id = order_item.orders_id"
                    +
                    " INNER JOIN PRODUCT_ITEM ON order_item.product_item_id = product_item.id" +
                    " where product_item.created_by = :sellerName and order_item.status = :orderStatus", nativeQuery = true)
    Page<OrderItemEntity> findAllBySellerNameAndStatus(
            @Param("sellerName") String sellerName, @Param(value = "orderStatus") OrderStatus orderStatus,
            Pageable pageable);

    List<OrderItemEntity> findAllByProductItemId(Long id);

}
