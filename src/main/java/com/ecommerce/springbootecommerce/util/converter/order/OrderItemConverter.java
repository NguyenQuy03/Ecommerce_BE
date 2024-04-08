package com.ecommerce.springbootecommerce.util.converter.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.util.converter.CurrencyFormater;

@Component
public class OrderItemConverter {

    @Autowired
    private ModelMapper mapper;

    public OrderItemEntity toEntity(OrderItemDTO dto) {
        return mapper.map(dto, OrderItemEntity.class);
    }

    public OrderItemDTO toDTO(OrderItemEntity entity) {
        OrderItemDTO dto = mapper.map(entity, OrderItemDTO.class);
        dto.setTotalPrice(CurrencyFormater
                .formatDollars(new BigDecimal(entity.getCurPrice()).doubleValue() * entity.getQuantity()));
        return dto;
    }

    public List<OrderItemDTO> toListDTO(List<OrderItemEntity> orderItemEntities) {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItemEntity entity : orderItemEntities) {
            orderItemDTOs.add(toDTO(entity));
        }

        return orderItemDTOs;
    }
}
