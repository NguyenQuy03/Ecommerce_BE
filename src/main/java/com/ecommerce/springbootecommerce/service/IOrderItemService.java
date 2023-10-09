package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.OrderItemDTO;

public interface IOrderItemService {
    void save(OrderItemDTO orderItemDTO);
    
    OrderItemDTO findOneById(Long orderItemId);

    OrderItemDTO findAllBySellerNameAndStatus(String sellerName, String orderStatus, int page, int size);

}
