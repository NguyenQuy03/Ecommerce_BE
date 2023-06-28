package com.ecommerce.springbootecommerce.controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("seller/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;


    @GetMapping("list/all")
    public String allProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);

        long quantityProduct = productService.countAllByAccountIdAndStatusNotAndStatusNot(account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT);

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> listProduct = productService.findAllByAccountIdAndStatusNotAndStatusNot(account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT, pageable);

        genericDTO(model, listProduct, page, size, quantityProduct);
        
        model.addAttribute("tab", "all");

        return "seller/product/myProduct";
    }

    @GetMapping("list/live")
    public String liveProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);

        Long quantityLiveProduct = productService.countByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(0L, account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT);
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> listProduct = productService.findByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(0L, account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT, pageable);

        genericDTO(model, listProduct, page, size, quantityLiveProduct);
        
        model.addAttribute("tab", "live");

        return "seller/product/myProduct";
    }

    @GetMapping("list/soldout")
    public String soldOutProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);
        
        long quantitySoldOutProduct = productService.countByStockEqualsAndAccountIdAndStatusNotAndStatusNot(0, account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT);

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> listSoldOutProduct = productService.findByStockEqualsAndAccountIdAndStatusNotAndStatusNot(0, account.getId(), SystemConstant.REMOVED_PRODUCT, SystemConstant.DELETED_PRODUCT, pageable);
        
        genericDTO(model, listSoldOutProduct, page, size, quantitySoldOutProduct);
        
        model.addAttribute("tab", "soldout");

        return "seller/product/myProduct";
    }
    
    public static void genericDTO(Model model, List<ProductDTO> listProduct, int page, int size, long quantityProduct) {
         
        Integer totalPage = (int) Math.ceil((double) quantityProduct / size);
        
        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(listProduct);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantityProduct", quantityProduct);
        model.addAttribute("dto", dto);
    }
}
