package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    CategoryDTO save(CategoryDTO categoryDTO);
    void delete(String[] ids);
    List<CategoryDTO> findAll();
    long count();
    List<CategoryDTO> findAll(Pageable pageable);
}
