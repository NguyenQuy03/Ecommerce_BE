package com.ecommerce.springbootecommerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.util.converter.OrderItemConverter;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private OrderItemConverter orderItemConverter;
    
    @Override
    public OrderItemDTO findOneById(Long orderItemId) {
        Optional<OrderItemEntity> orderItem = orderItemRepo.findOneById(orderItemId);
        return orderItem.map(item -> orderItemConverter.toDTO(item)).orElse(null);
    }

    @Override
    public void save(OrderItemDTO orderItemDTO) {
        try {
            orderItemRepo.save(orderItemConverter.toEntity(orderItemDTO));
        } catch (Exception e) {
            throw new RuntimeException("Error create order item");
        }
    }

    @Override
    public OrderItemDTO findAllBySellerNameAndStatus(String sellerName, String orderStatus, int page, int size) {
        Page<OrderItemEntity> pageEntity = orderItemRepo.findAllBySellerNameAndStatus(sellerName, orderStatus, PageRequest.of(page, size));
        OrderItemDTO dto = new OrderItemDTO();
        dto.setListResult(orderItemConverter.toListDTO(pageEntity.getContent()));

        return dto;
    }

}
