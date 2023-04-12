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
@RequestMapping("/seller")
public class DeleteController {
    
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IProductService productService;
    
    @GetMapping("/trashbin")
    public String trashBin(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findByUserName(userName);

        long quantityProduct = productService.countAllByAccountIdAndStatus(account.getId(), SystemConstant.REMOVED_PRODUCT);

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> listProduct = productService.findAllByAccountIdAndStatus(account.getId(), SystemConstant.REMOVED_PRODUCT, pageable);

        Integer totalPage = (int) Math.ceil((double) quantityProduct / size);
        
        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(listProduct);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantityProduct", quantityProduct);
        model.addAttribute("dto", dto);
        
        return "seller/product/trashBin";
    }
    
}
