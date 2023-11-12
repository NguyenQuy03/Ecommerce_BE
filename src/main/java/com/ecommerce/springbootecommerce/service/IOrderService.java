package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderDTO;

public interface IOrderService {
    OrderDTO save(OrderDTO dto);
    OrderDTO purchase(OrderDTO dto);

    OrderDTO findOneByAccountIdAndStatus(Long accountId, OrderStatus status);
    OrderDTO findOneById(Long id);
    OrderDTO findOneByCartIdAndAccountIdAndStatus(Long cartId, Long accountId, OrderStatus status);
    
    List<OrderDTO> findAllByAccountIdAndStatus(Long accountId, OrderStatus status, int page, int size);
    List<OrderDTO> findAllByStatus(OrderStatus status);
    List<OrderDTO> findAllByCartIdAndStatus(Long cartId, OrderStatus status);
    List<OrderDTO> findAllByCartId(Long id);
    List<OrderDTO> findAllByCartIdAndAndStatus(Long id, OrderStatus status);
    
    Long countByAccountIdAndStatus(Long accountId, OrderStatus status);
    
    boolean isOrderExistByAccountIdAndStatus(Long accountId, OrderStatus status);
    boolean isExistedByCartIdAndAccountId(long cartId, long accountId);
}
