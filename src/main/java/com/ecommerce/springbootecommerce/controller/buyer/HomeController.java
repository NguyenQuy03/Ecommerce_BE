package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller(value = "homeControllerOfBuyer")
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService orderService;

    @GetMapping("home")
    public String homePage(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();
        Pageable pageable = PageRequest.of(0, 8);
        List<ProductDTO> products = productService.findAll(pageable);
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.contains("anonymousUser")) {
            AccountDTO accountDTO = accountService.findAccountByUserName(userName);
            Long quantityOrder = orderService.countByAccountId(accountDTO.getId());
            
            model.addAttribute("quantityOrder", quantityOrder);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "buyer/home";
    }
    
}
