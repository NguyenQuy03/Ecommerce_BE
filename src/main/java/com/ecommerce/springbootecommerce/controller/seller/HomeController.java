package com.ecommerce.springbootecommerce.controller.seller;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller(value = "homeControllerOfSeller")
@RequestMapping("seller")
@PreAuthorize("hasRole('SELLER')")
public class HomeController {
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;
	
	@GetMapping("recentSales")
    public String recentSalesPage(Model model) {
	    	    
	    String sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO acc = accountService.findByUsername(sellerName);
        List<OrderDTO> dtos = orderService.findAllByStatusAndAccountId(SystemConstant.STRING_DELIVERED_ORDER, acc.getId());
        
        OrderDTO dto = new OrderDTO();
        dto.setListResult(dtos);
        model.addAttribute("dto", dto);

		return "seller/recentSales";
	}

	@GetMapping("topSelling")
	public String topSellingPage(Model model) {
	    String sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO acc = accountService.findByUsername(sellerName);
        List<ProductDTO> dtos = productService.findTopSelling(acc.getId());
        
        ProductDTO dto = new ProductDTO();
        dto.setListResult(dtos);
        model.addAttribute("dto", dto);
		return "seller/topSelling";
	}
}
