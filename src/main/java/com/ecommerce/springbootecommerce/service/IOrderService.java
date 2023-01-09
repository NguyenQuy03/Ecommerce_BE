package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    OrderDTO findOneByProductIdAndAccountIdAndStatus(Long productId, Long accountId, String status);
    boolean isOrderExistByProductIdAndAccountIdAndStatus(Long productId, Long accountId, String status);
    Long countByAccountId(Long id);
}
