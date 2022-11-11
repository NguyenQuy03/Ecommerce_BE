package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;

public interface ICategoryService {
    List<CategoryDTO> findAll();
}
