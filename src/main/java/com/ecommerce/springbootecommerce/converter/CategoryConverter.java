package com.ecommerce.springbootecommerce.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;

@Component
public class CategoryConverter {
    
    public CategoryEntity toEntity(CategoryDTO category) {
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
        category.setProducts(entity.getProducts());
        category.setThumbnailBase64(entity.getThumbnail().toString());
        
        category.setCreatedBy(entity.getCreatedBy());
        category.setModifiedBy(entity.getModifiedBy());
        category.setCreatedDate(entity.getCreatedDate());
        category.setModifiedDate(entity.getModifiedDate());        
        
        return category;
    }

    public CategoryEntity toEntity(CategoryDTO categoryDTO, CategoryEntity preCategoryEntity) {
        preCategoryEntity.setCode(categoryDTO.getCode());
        preCategoryEntity.setName(categoryDTO.getName());
        preCategoryEntity.setThumbnail(categoryDTO.getThumbnail());
        return preCategoryEntity;
    }

    public List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> listCategoryEntity) {
        List<CategoryDTO> listCategoryDTO = new ArrayList<>();
        for (CategoryEntity category : listCategoryEntity) {
            CategoryDTO categoryDto = toDTO(category);
            listCategoryDTO.add(categoryDto);
        }
        return listCategoryDTO;
    }
}
