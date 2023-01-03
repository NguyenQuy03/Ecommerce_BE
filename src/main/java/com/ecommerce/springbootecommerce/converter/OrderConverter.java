package com.ecommerce.springbootecommerce.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

@Component
public class OrderConverter {
    
    public OrderEntity toEntity(OrderDTO dto) {
        OrderEntity entity = new OrderEntity();
        
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setStatus(dto.getStatus());
        entity.setAccount(dto.getAccount());
        entity.setProduct(dto.getProduct());
        entity.setCarts(dto.getCarts());
        
        return entity;
    }
    
    public OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        dto.setAccount(entity.getAccount());
        dto.setProduct(entity.getProduct());
        dto.setCarts(entity.getCarts());
        
        return dto;
    }
}
