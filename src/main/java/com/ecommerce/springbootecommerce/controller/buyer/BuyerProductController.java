package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.springbootecommerce.api.buyer.BuyerProductAPI;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    private BuyerProductAPI buyerProductAPI;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService orderService;
    
    @GetMapping("/detail/{id}")
    public String displayProduct(
          @PathVariable("id") Long id,
          Model model
    ) {
        ProductDTO productDTO = productService.findById(id);
        productDTO.setQuantity(1L);
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.contains("anonymousUser")) {
            AccountDTO accountDTO = accountService.findAccountByUserName(userName);
            Long quantityOrder = orderService.countByAccountId(accountDTO.getId());
            
            model.addAttribute("quantityOrder", quantityOrder);
        }
        model.addAttribute("product", productDTO);
        return "/buyer/detailProduct";
    }
    
    @PostMapping()
    public RedirectView addProductToCart(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("id") Long id,
            @RequestParam("quantity") Long quantity
    ) {
        buyerProductAPI.addProduct(id, quantity);
        
        redirectAttributes.addFlashAttribute("showModel", "show");
        
        return new RedirectView("/product/detail/" + id);
    }
}
