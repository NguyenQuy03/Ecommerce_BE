package com.ecommerce.springbootecommerce.controller.seller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
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
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size,
            @RequestParam(value = "message", required = false) String message
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);

        BaseDTO<ProductDTO> dto = productService.findAllValid(account.getId(), SystemConstant.INACTIVE_PRODUCT, SystemConstant.REMOVED_PRODUCT, page, size);

        
        if(message != null && mapMessage.get(message) != null) {
            model.addAttribute("message", mapMessage.get(message));
        }

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);

        BaseDTO<ProductDTO> dto = productService.findAllLive(account.getId(), SystemConstant.INACTIVE_PRODUCT, SystemConstant.REMOVED_PRODUCT, 0, page, size);
        
        model.addAttribute("tab", "live");
        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);
        
        return "seller/product/myProduct";
    }
    
    @GetMapping("list/soldout")
    public String soldOutProduct(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "2") int size
    ){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);
        
        BaseDTO<ProductDTO> dto = productService.findAllSoldOut(account.getId(), SystemConstant.INACTIVE_PRODUCT, SystemConstant.REMOVED_PRODUCT, 0, page, size);

        model.addAttribute("tab", "soldout");
        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/myProduct";
    }

    @GetMapping("/trashbin")
    public String trashBin(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size,
            @RequestParam(value = "message", required = false) String message
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUsername(username);
        BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndStatus(account.getId(), SystemConstant.INACTIVE_PRODUCT, page, size);

        if(message != null && mapMessage.get(message) != null) {
            model.addAttribute("message", mapMessage.get(message));
        }

        model.addAttribute("quantityProduct", dto.getTotalItem());
        model.addAttribute("dto", dto);

        return "seller/product/trashBin";
    }

    HashMap<String, String> mapMessage = new HashMap<>() {{
        put("addSucceed", "Success! Your product was published.");
        put("updateSucceed", "Success! Your product was updated.");
        put("deleteSucceed", "Success! Your product was deleted.");
        put("fdeleteSucceed", "Success! Your product was force deleted.");
        put("restoreSucceed", "Success! Your product was restored.");
    }};

}
