package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IProductItemService;

@RestController
@RequestMapping("/api/buyer/cart")
public class CartAPI {
    
    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IProductItemService productItemService;
    
    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderItemService orderItemService;
    
    @PostMapping()
    public ResponseEntity<String> add(
            @RequestBody ProductItemDTO dto
    ) {
       CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
       CartDTO cartDTO = cartService.findOneByAccountId(userDetails.getId());
       ProductItemDTO productItemDTO = productItemService.findOneById(dto.getId());

       CartItemDTO cartItemDTO = CartItemDTO.builder()
                                .cart(cartDTO)
                                .username(userDetails.getUsername())
                                .productItem(productItemDTO)
                                .quantity(dto.getQuantity())
                                .build();

        try {
            cartItemService.save(cartItemDTO);
            return ResponseEntity.ok("Item has been added to your shopping cart");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error add cart item");
        }
    }

    @PostMapping(value = "/buy-again")
    public ResponseEntity<String> buyAgain(
        @RequestBody long orderItemId
    ) {      
        try {
            OrderItemDTO orderItem = orderItemService.findOneById(orderItemId);

                /* HANDLE IF PRODUCT ITEM IS NOT AVAILABLE */
            String productItemStatus = orderItem.getProductItem().getStatus();
            if (productItemStatus.equals(SystemConstant.STRING_INACTIVE_STATUS) || productItemStatus.equals(SystemConstant.REMOVED_STATUS)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not avaialable now");
            }

                /* HANDLE IF CART ITEM IS EXISTED */
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

            CartItemDTO cartItem = CartItemDTO.builder()
                                    .cart(cart)
                                    .productItem(orderItem.getProductItem())
                                    .quantity(1)
                                    .username(userDetails.getUsername())
                                    .build();

            cartItemService.save(cartItem);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error buy again");
        }
    }
    
    @DeleteMapping
    public ResponseEntity<String> delete(
        @RequestBody Long cartItemId
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CartDTO cartDTO = cartService.findOneByAccountId(userDetails.getId());
        try {
            cartService.delete(cartItemId, cartDTO.getId(), userDetails.getUsername());
            return ResponseEntity.ok("Item deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete Item");
        }
    }
}