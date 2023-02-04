package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    OrderDTO findOneById(Long id);
    boolean isOrderExistByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    Long countByCartIdAndStatus(Long cartId, String status);
    
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status, Pageable pageable);
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status);
    void delete(long id);
}
