package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.converter.OrderConverter;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return orderConverter.toDTO(orderEntity);
    }
    
    @Override
    public OrderDTO findOneByProductIdAndCartIdAndStatus(Long productId, Long cartId, String status) {
        if(orderRepository.findOneByProductIdAndCartIdAndStatus(productId, cartId, status).isPresent()) {
            OrderEntity orderEntity = orderRepository.findOneByProductIdAndCartIdAndStatus(productId, cartId, status).get();
            return orderConverter.toDTO(orderEntity);
        }
        return null;
    }

    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status, pageable).getContent();
        return orderConverter.toListOrderDTO(orderEntities);
    }
    
    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(Long cartId, String status) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status);
        return orderConverter.toListOrderDTO(orderEntities);
    }

    @Override
    public List<OrderDTO> findAllByStatus(String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatus(status, pageable).getContent();
        return orderConverter.toListOrderDTO(orderEntities);
    }

    @Override
    public List<OrderDTO> findAllByStatusAndSellerName(String status, String sellerName) {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatusAndSellerName(status, sellerName).getContent();
        return orderConverter.toListOrderDTO(orderEntities);
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
