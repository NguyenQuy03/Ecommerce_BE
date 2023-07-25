package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> listCategoriesEntity = categoryRepository.findAll();
        return toListCategoryDTO(listCategoriesEntity);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity;

        if (categoryDTO.getId() != null) {
            CategoryEntity preCategoryEntity = categoryRepository.findOneById(categoryDTO.getId());
            modelMapper.map(categoryDTO, preCategoryEntity);
            categoryEntity = modelMapper.map(preCategoryEntity, CategoryEntity.class);
        } else {
            categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        }

        categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryDTO.class);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            categoryRepository.deleteById(id);
        }   
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public List<CategoryDTO> findAll(Pageable pageable) {
        List<CategoryEntity> listCategoryEntity = categoryRepository.findAll(pageable).getContent();
        return toListCategoryDTO(listCategoryEntity);
    }

    private List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> listCategoryEntity) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(CategoryEntity entity : listCategoryEntity) {
            categoryDTOS.add(modelMapper.map(entity, CategoryDTO.class));
        }
        return categoryDTOS;
    }

}
