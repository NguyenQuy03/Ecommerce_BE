package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;

public interface ICategoryService {
    void save(CategoryDTO categoryDTO);
    void delete(Long[] ids);

    CategoryDTO findOneByCode(String code);

    List<CategoryDTO> findAll();
    List<CategoryDTO> findAll(Pageable pageable);
    long count();
}
