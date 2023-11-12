package com.ecommerce.springbootecommerce.controller.seller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller(value = "ProductControllerOfSeller")
@RequestMapping("seller/product")
public class MyProductController {

    @Autowired
    private IProductService productService;
    
    @GetMapping("list/all")
    public String allProduct(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndStatus(userDetails.getId(), StatusConstant.STRING_ACTIVE_STATUS, page, size);

        return generic(model, dto, "all");
    }

    @GetMapping("list/live")
    public String liveProduct(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndProductStatusAndProductItemStatus(
            userDetails.getId(), StatusConstant.STRING_ACTIVE_STATUS, StatusConstant.STRING_ACTIVE_STATUS, page, size
        );
        
        return generic(model, dto, "live");
    }
    
    @GetMapping("list/soldout")
    public String soldOutProduct(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndProductStatusAndProductItemStatus(
            userDetails.getId(), StatusConstant.STRING_ACTIVE_STATUS, StatusConstant.SOLD_OUT_STATUS, page, size
        );

        return generic(model, dto, "soldout");
    }
    
    private String generic(Model model, BaseDTO<ProductDTO> dto, String tab) {
        
        model.addAttribute("tab", tab);
        model.addAttribute(SystemConstant.QUANTITY_PRODUCT_DTO, dto == null ? 0 : dto.getTotalItem());
        model.addAttribute("dto", dto);
        return "seller/product/myProduct";
    }

    @GetMapping("/trashbin")
    public String trashBin(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndStatus(userDetails.getId(), StatusConstant.STRING_INACTIVE_STATUS, page, size);

        model.addAttribute(SystemConstant.QUANTITY_PRODUCT_DTO, dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/trashBin";
    }
}
