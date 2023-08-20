package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);
    void purchase(OrderDTO dto);
    void delete(String id);
    
    OrderDTO findOneByAccountIdAndStatus(String accountId, String status);
    OrderDTO findOneById(String id);
    
    List<OrderDTO> findAllByAccountIdAndStatus(String accountId, String status);
    List<OrderDTO> findAllByStatus(String status);

    Long countByAccountIdAndStatus(String accountId, String status);

    boolean isOrderExistByAccountIdAndStatus(String accountId, String status);

}
