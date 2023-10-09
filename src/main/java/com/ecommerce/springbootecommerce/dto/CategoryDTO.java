package com.ecommerce.springbootecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO<CategoryDTO> {  
    
    private String code;
    private String thumbnail;
}
