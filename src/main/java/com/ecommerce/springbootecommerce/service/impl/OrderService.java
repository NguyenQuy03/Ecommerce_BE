package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.converter.OrderConverter;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.service.IOrderService;

@Service
public class OrderService implements IOrderService{
    
    @Autowired
    private OrderConverter orderConverter;
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(OrderDTO dto) {
        OrderEntity entity = orderConverter.toEntity(dto);
        orderRepository.save(entity);
    }

    @Override
    public OrderDTO findOneByProductIdAndAccountIdAndStatus(Long productId, Long accountId, String status) {
        OrderEntity orderEntity = orderRepository.findOneByProductIdAndAccountIdAndStatus(productId, accountId, status);
        OrderDTO orderDTO = orderConverter.toDTO(orderEntity);
        return orderDTO;
    }

    @Override
    public Long countByAccountId(Long id) {
        return orderRepository.countByAccountId(id);
    }

    @Override
    public boolean isOrderExistByProductIdAndAccountIdAndStatus(Long productID, Long accountId, String status) {
        return orderRepository.findByProductIdAndAccountIdAndStatus(productID, accountId, status).isPresent();
    }

    @Override
    public List<OrderDTO> findAllByAccountId(Long accountId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByAccountId(accountId);
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

}
