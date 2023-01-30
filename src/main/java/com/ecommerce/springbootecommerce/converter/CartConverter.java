package com.ecommerce.springbootecommerce.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;

@Component
public class CartConverter {
    public CartDTO toDTO(CartEntity entity) {
        CartDTO dto = new CartDTO();
        dto.setId(entity.getId());
        dto.setAccount(entity.getAccount());
        dto.setStatus(entity.getStatus());
        dto.setSetOrders(entity.getSetOrders());
        
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    
    public CartEntity toEntity(CartDTO dto) {
        CartEntity entity = new CartEntity();
        entity.setId(dto.getId());
        entity.setAccount(dto.getAccount());
        entity.setStatus(dto.getStatus());
        entity.setSetOrders(dto.getSetOrders());
        
        return entity;
    }
}
