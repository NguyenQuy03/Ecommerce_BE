package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductItemService;

@RestController
@RequestMapping("/api/buyer/cart")
public class CartAPI {

    @Autowired
    private IProductItemService productItemService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;
    
    @PostMapping()
    public ResponseEntity<String> add(
            @RequestBody ProductItemDTO dto
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            CartDTO cartDTO = cartService.findOneByAccountId(userDetails.getId());
            ProductItemDTO productItemDTO = productItemService.findOneById(dto.getId());
            AccountDTO accountDTO = accountService.findById(productItemDTO.getProduct().getAccount().getId());
    
            OrderDTO orderDTO = orderService.findOneByCartIdAndAccountIdAndStatus(cartDTO.getId(), accountDTO.getId(), OrderStatus.PENDING);
            var orderItemDTO = OrderItemDTO.builder()
                                    .orders(orderDTO)
                                    .productItem(productItemDTO)
                                    .quantity(dto.getQuantity())
                                    .curPrice(productItemDTO.getPrice())
                                    .status(OrderStatus.PENDING)
                                    .build();

            if (orderDTO == null) {
                orderDTO = OrderDTO.builder()
                                .account(accountDTO)
                                .status(OrderStatus.PENDING)
                                .cart(cartDTO)
                                .build();
                OrderDTO newOrderDTO = orderService.save(orderDTO);

                orderItemDTO.setOrders(newOrderDTO);
            }

            orderItemService.save(orderItemDTO);

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
            if (productItemStatus.equals(StatusConstant.STRING_INACTIVE_STATUS) || productItemStatus.equals(StatusConstant.REMOVED_STATUS)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not avaialable now");
            }

                /* HANDLE IF CART ITEM IS EXISTED */
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

            // CartItemDTO cartItem = CartItemDTO.builder()
            //                         .cart(cart)
            //                         .productItem(orderItem.getProductItem())
            //                         .quantity(1)
            //                         .username(userDetails.getUsername())
            //                         .build();

            // cartItemService.save(cartItem);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error buy again");
        }
    }
    
    @DeleteMapping
    public ResponseEntity<String> delete(
        @RequestBody Long orderItemId
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            CartDTO cartDTO = cartService.findOneByAccountId(userDetails.getId());
            List<OrderDTO> orderDTOs = orderService.findAllByCartIdAndAndStatus(cartDTO.getId(), OrderStatus.PENDING);
    
            OrderItemDTO orderItemDTO = orderItemService.findOneByIdAndStatus(orderItemId, OrderStatus.PENDING);
            
            if(orderItemDTO == null || orderDTOs.size() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order item is not exist");
            }

            for(OrderDTO orderDTO : orderDTOs) {
                if(orderDTO.getId().equals(orderItemDTO.getOrders().getId())) {
                    orderItemService.deleteById(orderItemId);
                    return ResponseEntity.ok("Item deleted successfully");
                }
            }
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You do not have permission to do this action");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete Item");
        }
    }
}