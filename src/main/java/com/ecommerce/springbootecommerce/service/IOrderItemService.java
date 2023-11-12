package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;

public interface IOrderItemService {
    void save(OrderItemDTO dto);

    void deleteById(Long id);
    
    OrderItemDTO findOneById(Long id);
    
    OrderItemDTO findOneByIdAndStatus(Long orderItemId, OrderStatus status);
    
    OrderItemDTO findAllBySellerNameAndStatus(String sellerName, OrderStatus status, int page, int size);
    
    OrderItemDTO findOneByOrderIdAndProductItemId(Long orderId, Long productItemId);

}
