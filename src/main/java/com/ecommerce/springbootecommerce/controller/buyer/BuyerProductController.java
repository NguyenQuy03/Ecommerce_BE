package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.springbootecommerce.api.buyer.OrderAPI;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.QuantityOrderUtil;

@Controller
@RequestMapping("/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    private OrderAPI orderAPI;
    
    @Autowired
    private QuantityOrderUtil quantityOrderUtil;
    
    @GetMapping("/detail/{id}")
    public String displayProduct(
          @PathVariable("id") Long id,
          Model model
    ) {
        ProductDTO productDTO = productService.findById(id);
        productDTO.setQuantity(1L);
                
        model.addAttribute("product", productDTO);
        model.addAttribute("quantityOrder", quantityOrderUtil.getQuantityOrder());
        return "/buyer/detailProduct";
    }
    
    @PostMapping()
    public RedirectView addOrderToCart(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("id") Long id,
            @RequestParam("quantity") Long quantity
    ) {
        orderAPI.addOrder(id, quantity);
        
        redirectAttributes.addFlashAttribute("showModal", "show");
        
        return new RedirectView("/product/detail/" + id);
    }
}
