package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
@RequestMapping("/api/buyer/order")
public class OrderAPI {
    
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
    public RedirectView addOrder(
            @RequestParam("id") Long productId,
            @RequestParam("quantity") Long quantity
    ) {
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findAccountByUserName(userName);
        boolean isOrderExist = orderService.isOrderExistByProductIdAndAccountIdAndStatus(productId, accountDTO.getId(), SystemConstant.ACTIVE_PRODUCT);
        
        if (!isOrderExist) {
            OrderDTO orderDTO = new OrderDTO();
            ProductDTO productDTO = productService.findById(productId);
            ProductEntity productEntity = productConverter.toEntity(productDTO);
            AccountEntity accountEntity = accountConverter.toEntity(accountDTO);
            orderDTO.setProduct(productEntity);
            
            orderDTO.setAccount(accountEntity);
            
            orderDTO.setStatus(SystemConstant.ACTIVE_PRODUCT);
            orderDTO.setQuantity(quantity);
            orderService.save(orderDTO);
        
        } else {
            OrderDTO existedOrder = orderService.findOneByProductIdAndAccountIdAndStatus(productId, accountDTO.getId(), SystemConstant.ACTIVE_PRODUCT);
            existedOrder.setQuantity(quantity + existedOrder.getQuantity());
            orderService.save(existedOrder);
        }
            
        return new RedirectView("/product/detail/" + productId);
    }
    
    @DeleteMapping
    public void deleteProduct(@RequestBody long id) {
        orderService.delete(id);
    }
}