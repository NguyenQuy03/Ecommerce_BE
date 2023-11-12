package com.ecommerce.springbootecommerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.order.OrderItemConverter;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepo;
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderItemConverter orderItemConverter;

    @Override
    public void save(OrderItemDTO dto) {
        try {
            Optional<OrderItemEntity> entity = orderItemRepo.findOneByOrdersIdAndProductItemIdAndStatus(
                dto.getOrders().getId(), dto.getProductItem().getId(), OrderStatus.PENDING
            );
            if(entity.isPresent()) {
                OrderItemEntity newEntity = entity.get();

                newEntity.setQuantity(newEntity.getQuantity() + dto.getQuantity());
                orderItemRepo.save(newEntity);
            } else {
                orderItemRepo.save(orderItemConverter.toEntity(dto));
                redisUtil.adjustQuantityOrder(dto.getOrders().getCart().getAccount().getUsername(), +1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error create order item");
        }
    }

    @Override
    public void deleteById(Long orderItemId) {
        try {
            orderItemRepo.deleteById(orderItemId);
        } catch (Exception e) {
            throw new RuntimeException("Error delete order item");
        }
    }

    // FIND ONE
    @Override
    public OrderItemDTO findOneById(Long orderItemId) {
        Optional<OrderItemEntity> orderItem = orderItemRepo.findOneById(orderItemId);
        return orderItem.map(item -> orderItemConverter.toDTO(item)).orElse(null);
    }

    @Override
    public OrderItemDTO findOneByIdAndStatus(Long id, OrderStatus status) {
        Optional<OrderItemEntity> orderItem = orderItemRepo.findOneByIdAndStatus(id, status);
        return orderItem.map(item -> orderItemConverter.toDTO(item)).orElse(null);
    }

    @Override
    public OrderItemDTO findOneByOrderIdAndProductItemId(Long orderId, Long productItemId) {
        Optional<OrderItemEntity> optionalEntity = orderItemRepo.findOneByOrdersIdAndProductItemId(orderId, productItemId);
        return optionalEntity.map(item -> orderItemConverter.toDTO(item)).orElse(null);
    }

    // FIND ALL
    @Override
    public OrderItemDTO findAllBySellerNameAndStatus(String sellerName, OrderStatus orderStatus, int page, int size) {
        Page<OrderItemEntity> pageEntity = orderItemRepo.findAllBySellerNameAndStatus(sellerName, orderStatus, PageRequest.of(page, size));
        OrderItemDTO dto = new OrderItemDTO();
        dto.setListResult(orderItemConverter.toListDTO(pageEntity.getContent()));

        return dto;
    }
}
