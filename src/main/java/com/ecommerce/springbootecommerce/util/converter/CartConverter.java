package com.ecommerce.springbootecommerce.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.util.converter.account_role.AccountConverter;

@Component
public class CartConverter {
    @Autowired
    private AccountConverter accountConverter;
    
    @Autowired
    private ModelMapper mapper;
    
    public CartEntity toEntity(CartDTO dto) {
        CartEntity entity = mapper.map(dto, CartEntity.class);
        entity.setAccount(accountConverter.toEntity(dto.getAccount()));

        return entity;
    }

    public CartDTO toDTO(CartEntity entity) {
        CartDTO dto = mapper.map(entity, CartDTO.class);
        dto.setAccount(accountConverter.toDTO(entity.getAccount()));

        return dto;
    }
}
