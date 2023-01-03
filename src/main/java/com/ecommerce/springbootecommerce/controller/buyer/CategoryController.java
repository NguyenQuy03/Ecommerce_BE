package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
public class CategoryController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/category/{id}")
    public String categoryPage(
            Model model,
            @PathVariable("id") long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        
        Pageable pageable = PageRequest.of(page - 1, size);   
        
        long quantityProduct = productService.countAllByCategoryId(id);
        
        Integer totalPage = (int) Math.ceil((double) quantityProduct / size);
        List<ProductDTO> products = productService.findAllByCategoryId(id, pageable);
        
        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(products);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantitySoldOutProduct", quantityProduct);
        model.addAttribute("dto", dto);

        return "buyer/category";
    }
}
