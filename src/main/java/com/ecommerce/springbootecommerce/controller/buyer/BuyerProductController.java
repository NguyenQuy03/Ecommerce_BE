package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    
    @GetMapping("/detail/{id}")
    public String displayProduct(
          @PathVariable("id") Long id,
          Model model
    ) {
        ProductDTO productDTO = productService.findById(id);
        
        model.addAttribute("product", productDTO);
        return "/buyer/detailProduct";
    }
}
