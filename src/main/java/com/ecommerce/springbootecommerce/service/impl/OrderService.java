package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public void delete(long id) {
        orderRepository.deleteById(id);
    }
    
    @Override
    public OrderDTO findOneById(Long id) {
        OrderEntity orderEntity = orderRepository.findOneById(id);
        OrderDTO orderDTO = orderConverter.toDTO(orderEntity);
        return orderDTO;
    }
    
    @Override
    public OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status) {
        OrderEntity orderEntity = orderRepository.findOneByProductIdAndCartIdAndStatus(productId, cartId, status).get();
        OrderDTO orderDTO = orderConverter.toDTO(orderEntity);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status, pageable).getContent();
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }
    
    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status);
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> findAllByStatus(String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatus(status, pageable).getContent();
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> findAllByStatusAndSellerName(String status, String sellerName) {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatusAndSellerName(status, sellerName).getContent();
        List<OrderDTO> orderDTOs = orderConverter.toListOrderDTO(orderEntities);
        return orderDTOs;
    }

    @Override
    public Long countByCartIdAndStatus(Long cartId, String status) {
        return orderRepository.countByCartIdAndStatus(cartId, status);
    }

    @Override
    public boolean isOrderExistByProductIdAndCartIdAndStatus(Long productID, Long cartId, String status) {
        return orderRepository.findOneByProductIdAndCartIdAndStatus(productID, cartId, status).isPresent();
    }
}
