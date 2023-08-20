package com.ecommerce.springbootecommerce.dto;

import java.util.Set;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO<CategoryDTO> {  
    
    private String code;
    private String thumbnail;
    private Set<ProductEntity> products;
}
