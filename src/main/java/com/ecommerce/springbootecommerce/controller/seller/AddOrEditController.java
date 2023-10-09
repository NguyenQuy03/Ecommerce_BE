package com.ecommerce.springbootecommerce.controller.seller;

import java.util.List;

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
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("seller/product")
public class AddOrEditController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;
    
    @GetMapping()
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "seller/product/addOrEdit";
    }

    @GetMapping("/{id}")
    public String editProduct(
            @PathVariable("id") long id,
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductDTO dto = productService.findOneByIdAndAccountId(id, userDetails.getId());
        
        if (dto == null) {
            model.addAttribute("message", SystemConstant.ACCESS_EXCEPTION);
            return "error/error";
        }

        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", dto);
        model.addAttribute("isVariation", dto.getProductItems().get(0).getVariationName() != null);

        return "/seller/product/addOrEdit";
    }
}
