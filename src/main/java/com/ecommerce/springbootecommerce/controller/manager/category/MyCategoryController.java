package com.ecommerce.springbootecommerce.controller.manager.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@Controller
@RequestMapping("/manager/category")
public class MyCategoryController {
    
    @Autowired
    private ICategoryService categoryService;
    
    @GetMapping("/list/all")
    public String categories(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
        ) {
        
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BaseDTO<CategoryDTO> dto = categoryService.findAllByAccountId(userDetails.getId(), PageRequest.of(page - 1, size));
        
        model.addAttribute("quantityCategory", dto.getListResult().size());
        model.addAttribute("dto", dto);
                
        return "manager/category/myCategory";
    }
}
