package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO<CategoryDTO> {  
    
    private String name;
    private String code;
    private byte[] thumbnail;
    private List<ProductEntity> products;

}
