package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    void save(OrderDTO dto);

    void purchase(OrderDTO dto);
    void delete(String id);
    
    OrderDTO findOneByProductIdAndCartIdAndStatus(String productId, String cartId, String status);
    OrderDTO findOneById(String id);
    
    List<OrderDTO> findAllByCartIdAndStatus(String cartId, String status, Pageable pageable);
    List<OrderDTO> findAllByCartIdAndStatus(String cartId, String status);
    List<OrderDTO> findAllByStatus(String status, Pageable pageable);

    Long countByCartIdAndStatus(String cartId, String status);

    boolean isOrderExistByProductIdAndCartIdAndStatus(String productId, String cartId, String status);

    List<OrderDTO> findAllByStatusAndAccountId(String stringDeliveredOrder, String id);
}
