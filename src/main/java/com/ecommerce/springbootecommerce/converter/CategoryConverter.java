package com.ecommerce.springbootecommerce.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;

@Component
public class CategoryConverter {
    
    public CategoryEntity entity(CategoryDTO category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setCode(category.getCode());
        entity.setName(category.getName());
        entity.setThumbnail(category.getThumbnail());
        
        return entity;
    }
    
    public CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO category = new CategoryDTO();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setCode(entity.getCode());
        category.setThumbnail(entity.getThumbnail());
        category.setProducts(category.getProducts());
        
        category.setCreatedBy(entity.getCreatedBy());
        category.setModifiedBy(entity.getMordifiedBy());
        category.setCreatedDate(entity.getCreatedDate());
        category.setModifiedDate(entity.getMordifiedDate());        
        
        return category;
    }
}
