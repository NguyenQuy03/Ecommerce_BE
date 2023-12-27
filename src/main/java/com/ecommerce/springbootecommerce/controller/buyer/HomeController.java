package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.RedisUtil;

@RequestMapping
@Controller(value = "homeControllerOfBuyer")
public class HomeController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/home")
    public String homePage(
            Model model
    ) {
        List<CategoryDTO> categories = categoryService.findAll();
        Pageable pageable = PageRequest.of(0, 12);
        List<ProductDTO> productItems = productService.findAllByStatus(ProductStatus.ACTIVE, pageable);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.contains("anonymousUser")) {
            model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("productItems", productItems);

        return "buyer/home";
    }
    
    @GetMapping
    public String redirectHomePage() {
        return "redirect:/home";
    }

}
