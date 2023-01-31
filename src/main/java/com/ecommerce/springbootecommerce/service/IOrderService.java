package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

public interface IOrderService {
    void save(OrderDTO dto);
    OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    OrderEntity findOneById(Long id);
    boolean isOrderExistByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Long countByCartIdAndStatus(Long cartId, String status);
    
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status);
    void delete(long id);
}
