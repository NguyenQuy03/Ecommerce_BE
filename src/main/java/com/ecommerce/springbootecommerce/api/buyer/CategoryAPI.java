package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@RestController(value = "CategoryAPIOBuyer")
@RequestMapping("/api/v1/buyer/category")
public class CategoryAPI {

    @Autowired
    private ICategoryService categoryService;
    
    @GetMapping
    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = categoryService.findAll();

        return categories;
    }
}
