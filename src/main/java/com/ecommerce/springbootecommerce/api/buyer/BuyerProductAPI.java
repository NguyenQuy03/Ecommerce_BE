package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.converter.ProductConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.service.impl.AccountService;

@RestController
@RequestMapping("/api/buyer/product")
public class BuyerProductAPI {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ProductConverter productConverter;
    
    @Autowired
    private AccountConverter accountConverter;
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping()
    public void addProduct(
            @PathVariable("id") Long id,
            @PathVariable("quantity") Long quantity
    ) {
        OrderDTO orderDTO = new OrderDTO();
        ProductDTO productDTO = productService.findById(id);
        ProductEntity productEntity = productConverter.toEntity(productDTO);
        orderDTO.setProduct(productEntity);
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findAccountByUserName(userName);
        AccountEntity accountEntity = accountConverter.toEntity(accountDTO);
        orderDTO.setAccount(accountEntity);
        
        orderDTO.setStatus(SystemConstant.ACTIVE_PRODUCT);
        orderDTO.setQuantity(quantity);
        
        orderService.save(orderDTO);
    }
}