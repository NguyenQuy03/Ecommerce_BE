package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;

public interface ICategoryService {
    CategoryDTO save(CategoryDTO categoryDTO);
    void delete(long[] ids);
    List<CategoryDTO> findAll();
    long count();
    List<CategoryDTO> findAll(Pageable pageable);
}
