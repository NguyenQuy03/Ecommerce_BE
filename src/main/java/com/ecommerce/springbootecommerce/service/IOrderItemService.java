package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.OrderItemDTO;

public interface IOrderItemService {
    boolean isExistedByProductItemId(String productItemId);

    OrderItemDTO findOneById(String orderItemId);

    void save(OrderItemDTO orderItemDTO);
}
