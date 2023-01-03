package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
}
