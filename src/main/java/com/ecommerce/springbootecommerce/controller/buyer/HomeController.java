package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.QuantityOrderUtil;

@Controller(value = "homeControllerOfBuyer")
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;
    
    @Autowired
    private QuantityOrderUtil quantityOrder;

    @GetMapping("home")
    public String homePage(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();
        Pageable pageable = PageRequest.of(0, 12);
        List<ProductDTO> products = productService.findAllByStatus(SystemConstant.STRING_ACTIVE_STATUS, pageable);
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.contains("anonymousUser")) {
            model.addAttribute("quantityOrder", quantityOrder.getQuantityOrder());
        }
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        
        return "buyer/home";
    }
    
    @GetMapping
    public String redirectHomePage() {
        return "redirect:/home";
    }
}
