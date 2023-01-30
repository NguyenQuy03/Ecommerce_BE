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
        orderRepository.saveAndFlush(entity);
    }

    @Override
    public OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status) {
        OrderEntity orderEntity = orderRepository.findOneByProductIdAndCartIdAndStatus(productId, cartId, status);
        OrderDTO orderDTO = orderConverter.toDTO(orderEntity);
        return orderDTO;
    }

    @Override
    public Long countByCartId(Long cartId) {
        return orderRepository.countByCartId(cartId);
    }

    @Override
    public boolean isOrderExistByProductIdAndCartIdAndStatus(Long productID, Long cartId, String status) {
        return orderRepository.findByProductIdAndCartIdAndStatus(productID, cartId, status).isPresent();
    }

    @Override
    public List<OrderDTO> findAllByCartId(Long cartId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartId(cartId);
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }


}
