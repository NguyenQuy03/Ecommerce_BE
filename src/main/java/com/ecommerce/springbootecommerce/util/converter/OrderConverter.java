package com.ecommerce.springbootecommerce.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        double totalPrice;

        for(OrderEntity entity : orderEntities) {
            List<OrderItemEntity> orderItemEntities = orderItemRepo.findAllByOrdersId(entity.getId());
            totalPrice = 0;
            OrderDTO dto = toDTO(entity);
            dto.setOrderItems(orderItemConverter.toListDTO(orderItemEntities));
            for(OrderItemEntity orderItemEntity : orderItemEntities) {
                totalPrice += orderItemEntity.getCurPrice();
            }
            dto.setTotalPrice("$" + totalPrice);
            orderDTOs.add(dto);
        }
        
        return orderDTOs;
    }
}
