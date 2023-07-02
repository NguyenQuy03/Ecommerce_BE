package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> listCategoriesEntity = categoryRepository.findAll();
        return categoryConverter.toListCategoryDTO(listCategoriesEntity);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();

        if (categoryDTO.getId() != null) {
            CategoryEntity preCategoryEntity = categoryRepository.findOneById(categoryDTO.getId());
            categoryEntity = categoryConverter.toEntity(categoryDTO, preCategoryEntity);
        } else {

            categoryEntity = categoryConverter.toEntity(categoryDTO);

        }

        categoryRepository.save(categoryEntity);
        return categoryConverter.toDTO(categoryEntity);
    }

    @Override
    public void delete(long[] ids) {
        for (long id : ids) {
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
        return categoryConverter.toListCategoryDTO(listCategoryEntity);
    }

}
