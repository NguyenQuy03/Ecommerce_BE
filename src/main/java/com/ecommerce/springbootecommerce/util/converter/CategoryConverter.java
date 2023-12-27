package com.ecommerce.springbootecommerce.util.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;

@Component
public class CategoryConverter {
    
    public CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }
    
    
    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setThumbnail(entity.getThumbnail());
        return dto;
    }
}