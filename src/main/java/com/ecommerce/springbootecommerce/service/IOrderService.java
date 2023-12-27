package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    OrderDTO save(OrderDTO dto);

    OrderDTO purchase(OrderDTO dto);

    OrderDTO findOneById(Long id);

    OrderDTO findOneByBuyerIdAndSellerIdAndStatus(Long buyerId, Long sellerId, OrderStatus status);

    List<OrderDTO> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status, int page, int size);

    List<OrderDTO> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status);

    List<OrderDTO> findAllByStatus(OrderStatus status);

    Long countByBuyerIdAndStatus(Long buyerId, OrderStatus status);
}
