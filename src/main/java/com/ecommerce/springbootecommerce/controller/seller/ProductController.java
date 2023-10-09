package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller(value = "ProductControllerOfSeller")
@RequestMapping("seller/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("list/all")
    public String allProduct(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductDTO dto = productService.findAllByAccountIdAndStatus(userDetails.getId(), SystemConstant.STRING_ACTIVE_STATUS, page, size);

        model.addAttribute("tab", "all");
        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/myProduct";

    }

    @GetMapping("list/live")
    public String liveProduct(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductDTO dto = productService.findAllByAccountIdAndProductStatusAndProductItemStatus(
            userDetails.getId(), SystemConstant.STRING_ACTIVE_STATUS, SystemConstant.STRING_ACTIVE_STATUS, page, size
        );
        
        model.addAttribute("tab", "live");
        model.addAttribute("quantityProduct", dto == null ? 0 : dto.getTotalItem());
        model.addAttribute("dto", dto);
        
        return "seller/product/myProduct";
    }
    
    @GetMapping("list/soldout")
    public String soldOutProduct(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        ProductDTO dto = productService.findAllByAccountIdAndProductStatusAndProductItemStatus(
            userDetails.getId(), SystemConstant.STRING_ACTIVE_STATUS, SystemConstant.SOLD_OUT_STATUS, page, size
        );

        model.addAttribute("tab", "soldout");
        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/myProduct";
    }

    @GetMapping("/trashbin")
    public String trashBin(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductDTO dto = productService.findAllByAccountIdAndStatus(userDetails.getId(), SystemConstant.STRING_INACTIVE_STATUS, page, size);

        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/trashBin";
    }
}
