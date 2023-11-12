package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller(value = "homeControllerOfSeller")
@RequestMapping("seller")
@PreAuthorize("hasRole('SELLER')")
public class HomeController {
    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IProductService productService;
	
	@GetMapping("/recentSales")
    public String recentSalesPage(Model model) {
	    	    
	    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderItemDTO dto = orderItemService.findAllBySellerNameAndStatus(userDetails.getUsername(), OrderStatus.DELIVERED, 0, 50);

        model.addAttribute("dto", dto);
        
		return "seller/recentSales";
	}
    
	@GetMapping("/topSelling")
	public String topSellingPage(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductDTO dto = productService.findTopSelling(userDetails.getUsername());

        
        model.addAttribute("dto", dto);
		return "seller/topSelling";
	}
}
