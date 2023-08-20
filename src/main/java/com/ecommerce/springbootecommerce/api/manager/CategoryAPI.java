package com.ecommerce.springbootecommerce.api.manager;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@RestController
@RequestMapping(value = "/api/manager")
public class CategoryAPI {
    
    @Autowired
    private ICategoryService categoryService;
    
    @PostMapping(value = "/category")
    public void createCategory(
            @RequestBody CategoryDTO category 
    ) throws IOException {
        category.setId(null);
        categoryService.save(category);
    }

}
