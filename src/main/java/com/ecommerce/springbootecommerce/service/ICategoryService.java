package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;

public interface ICategoryService {
    void save(CategoryDTO categoryDTO);
    void delete(Long[] ids);

    CategoryDTO findOneByCode(String code);
    CategoryDTO findOneByIdAndAccountId(long id, long id2);

    List<CategoryDTO> findAll();
    List<CategoryDTO> findAllByAccountId(long id);
    BaseDTO<CategoryDTO> findAllByAccountId(long accounId, Pageable pageable);
}
