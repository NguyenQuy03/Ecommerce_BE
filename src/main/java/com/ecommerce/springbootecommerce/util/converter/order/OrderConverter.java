package com.ecommerce.springbootecommerce.util.converter.order;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;

@Component
public class OrderConverter {

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private OrderItemConverter orderItemConverter;
    
    @Autowired
    private ModelMapper mapper;
    
    public OrderEntity toEntity(OrderDTO dto) {
        OrderEntity entity = mapper.map(dto, OrderEntity.class);

        return entity;
    }

    public OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = mapper.map(entity, OrderDTO.class);

        return dto;
    }

    public List<OrderDTO> toListDTO(List<OrderEntity> orderEntities) {
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for(OrderEntity entity : orderEntities) {
            List<OrderItemEntity> orderItemEntities = orderItemRepo.findAllByOrdersIdAndStatus(entity.getId(), OrderStatus.PENDING);
            OrderDTO dto = toDTO(entity);
            dto.setOrderItems(orderItemConverter.toListDTO(orderItemEntities));
            
            dto.setTotalPrice("$" + orderItemEntities.stream().map(OrderItemEntity::getCurPrice)
                            .reduce(0D, (a, b) -> a + b));
            orderDTOs.add(dto);
        }
        
        return orderDTOs;
    }
}
