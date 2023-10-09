package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    
    OrderDTO findOneByAccountIdAndStatus(Long accountId, String status);
    OrderDTO findOneById(Long id);
    
    List<OrderDTO> findAllByAccountIdAndStatus(Long accountId, String status, int page, int size);
    List<OrderDTO> findAllByStatus(String status);
    
    Long countByAccountIdAndStatus(Long accountId, String status);

    boolean isOrderExistByAccountIdAndStatus(Long accountId, String status);

}
