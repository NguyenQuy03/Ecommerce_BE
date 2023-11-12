package com.ecommerce.springbootecommerce.controller.manager.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@Controller
@RequestMapping("/manager/category")
public class CategoryController {
    
    @Autowired
    private ICategoryService categoryService;
    
    @GetMapping("/{id}")
    public String editCategory(
            @PathVariable("id") long id,
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CategoryDTO dto = categoryService.findOneByIdAndAccountId(id, userDetails.getId());
        
        if (dto == null) {
            model.addAttribute("message", SystemConstant.ACCESS_EXCEPTION);
            return "error/error";
        }

        model.addAttribute("dto", dto);

        return "manager/category/category";
    }

    @GetMapping
    public String getCategoryForm(Model model) {
        CategoryDTO dto = new CategoryDTO();
        
        model.addAttribute("dto", dto);
        return "manager/category/category";
    }
}
