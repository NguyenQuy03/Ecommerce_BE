package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean isExistedByProductItemId(String productItemId) {
        return orderItemRepository.findOneByProductItemId(productItemId).isPresent();
    }

    @Override
    public OrderItemDTO findOneById(String orderItemId) {
        Optional<OrderItemEntity> orderItem = orderItemRepository.findOneById(orderItemId);
        return orderItem.map(item -> modelMapper.map(orderItem, OrderItemDTO.class)).orElse(null);
    }

    @Override
    public void save(OrderItemDTO orderItemDTO) {
        orderItemRepository.save(modelMapper.map(orderItemDTO, OrderItemEntity.class));
    }

}
