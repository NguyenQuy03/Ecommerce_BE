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

import com.ecommerce.springbootecommerce.api.buyer.OrderAPI;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.RedisUtil;

@Controller
@RequestMapping("/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    private OrderAPI orderAPI;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/detail/{id}")
    public String displayProduct(
          @PathVariable("id") String id,
          Model model
    ) {
        ProductDTO dto = productService.findOneById(id);
        
        if (!dto.getStatus().equals(SystemConstant.STRING_ACTIVE_STATUS)) {
            return "redirect:/error";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
                
        model.addAttribute("product", dto);
        model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER));
        return "/buyer/detailProduct";
    }
    
    @PostMapping()
    public RedirectView addOrderToCart(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("id") String id,
            @RequestParam("quantity") Long quantity
    ) {
        orderAPI.add(id, quantity);
        
        redirectAttributes.addFlashAttribute("showModal", "show");
        
        return new RedirectView("/product/detail/" + id);
    }
}
