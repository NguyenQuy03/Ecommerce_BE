package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    void delete(long id);
    
    OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    OrderDTO findOneById(Long id);
    
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status, Pageable pageable);
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status);
    List<OrderDTO> findAllByStatus(String status, Pageable pageable);
    List<OrderDTO> findAllByStatusAndSellerName(String status, String sellerName);
    
    Long countByCartIdAndStatus(Long cartId, String status);
    
    boolean isOrderExistByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status);
    
}
