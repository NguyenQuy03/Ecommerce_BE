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
import com.ecommerce.springbootecommerce.converter.CartConverter;
import com.ecommerce.springbootecommerce.converter.ProductConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/buyer/order")
public class OrderAPI {

    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ICartService cartService;
    
    @Autowired
    private ProductConverter productConverter;
    
    @Autowired
    private CartConverter cartConverter;
    
    @PostMapping()
    public RedirectView add(
            @RequestParam("id") Long productId,
            @RequestParam("quantity") Long quantity
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUserName(userName);
        CartDTO cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        
        boolean isOrderExist = orderService.isOrderExistByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);      
        
        if (!isOrderExist) {
            OrderDTO orderDTO = new OrderDTO();
            ProductDTO productDTO = productService.findById(productId);
            ProductEntity productEntity = productConverter.toEntity(productDTO);
            CartEntity cartEntity = cartConverter.toEntity(cartDTO); 
            orderDTO.setProduct(productEntity);
            orderDTO.setCart(cartEntity);;
            
            orderDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            orderDTO.setQuantity(quantity);
                        
            orderService.save(orderDTO);
        
        } else {
            OrderDTO existedOrder = orderService.findOneByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
            if (quantity + existedOrder.getQuantity() > existedOrder.getProduct().getStock()) {
                existedOrder.setQuantity(existedOrder.getProduct().getStock());
            } else {    
                existedOrder.setQuantity(quantity + existedOrder.getQuantity());
            }
            orderService.save(existedOrder);
        }
            
        return new RedirectView("/product/detail/" + productId);
    }
    
    @DeleteMapping
    public void delete(@RequestBody long id) {
        orderService.delete(id);
    }
}