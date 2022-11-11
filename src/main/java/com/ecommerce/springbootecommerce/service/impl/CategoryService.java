package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.converter.CategoryConverter;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryDTO> listCategory = new ArrayList<>();
        for (CategoryEntity category : categories) {
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto = categoryConverter.toDTO(category);
            listCategory.add(categoryDto);
        }
        return listCategory;
    }

}
