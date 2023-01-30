package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    boolean isOrderExistByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Long countByCartId(Long cartId);
    
    List<OrderDTO> findAllByCartId(Long cartId);
    void delete(long id);
}
