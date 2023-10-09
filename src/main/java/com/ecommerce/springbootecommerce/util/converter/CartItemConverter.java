package com.ecommerce.springbootecommerce.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.entity.CartItemEntity;

@Component
public class CartItemConverter {
    @Autowired
    private ModelMapper mapper;
    
    
    public CartItemEntity toEntity(CartItemDTO dto) {
        CartItemEntity entity = mapper.map(dto, CartItemEntity.class);
        return entity;
    }

    public CartItemDTO toDTO(CartItemEntity entity) {
        CartItemDTO dto = mapper.map(entity, CartItemDTO.class);
        return dto;
    }
}
