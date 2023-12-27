package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductItemService;
import com.ecommerce.springbootecommerce.util.AccountUtil;

@RestController
@RequestMapping("/api/v1/buyer/order-item")
public class OrderItemAPI {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IProductItemService productItemService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private AccountUtil accountUtil;

    @PostMapping()
    public ResponseEntity<String> add(
            @RequestBody ProductItemDTO dto) {
        try {
            ProductItemDTO productItemDTO = productItemService.findOneById(dto.getId());
            AccountDTO buyerDTO = accountUtil.getCurAccount();
            AccountDTO sellerDTO = accountService.findById(productItemDTO.getProduct().getAccount().getId());

            OrderDTO orderDTO = orderService.findOneByBuyerIdAndSellerIdAndStatus(buyerDTO.getId(),
                    sellerDTO.getId(), OrderStatus.PENDING);

            var orderItemDTO = OrderItemDTO.builder()
                    .orders(orderDTO)
                    .productItem(productItemDTO)
                    .quantity(dto.getQuantity())
                    .curPrice(productItemDTO.getPrice())
                    .status(OrderStatus.PENDING)
                    .build();

            if (orderDTO == null) {
                orderDTO = OrderDTO.builder()
                        .buyer(buyerDTO)
                        .seller(sellerDTO)
                        .status(OrderStatus.PENDING)
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
    public ResponseEntity<?> buyAgain(
            @RequestBody long orderItemId) {
        try {
            OrderItemDTO orderItemDTO = orderItemService.findOneById(orderItemId);

            /* HANDLE IF ORDER_ITEM HAS ALREADY EXISTED */
            long buyerId = accountUtil.getCurAccountId();
            OrderDTO orderDTO = orderService.findOneByBuyerIdAndSellerIdAndStatus(buyerId,
                    orderItemDTO.getProductItem().getProduct().getAccount().getId(), OrderStatus.PENDING);
            if (orderDTO != null) {
                orderItemDTO.increaseQuantity(1L);
                orderItemService.save(orderItemDTO);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Order item has already exist");
            }

            /* HANDLE IF PRODUCT_ITEM IS NOT AVAILABLE */
            ProductStatus productItemStatus = orderItemDTO.getProductItem().getStatus();
            if (ProductStatus.getInActiveStatus().contains(productItemStatus)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not avaialable now");
            }

            /* PASS ALL CASES */
            orderItemDTO.setQuantity(1L);
            orderItemService.save(orderItemDTO);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error buy again");
        }
    }

    @PatchMapping(value = "update-quantity")
    public ResponseEntity<?> update(
            @RequestBody OrderItemDTO dto) {

        try {

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Update Quantity");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestBody Long orderItemId) {
        try {
            long buyerId = accountUtil.getCurAccountId();
            List<OrderDTO> orderDTOs = orderService.findAllByBuyerIdAndStatus(buyerId, OrderStatus.PENDING);

            for (OrderDTO orderDTO : orderDTOs) {
                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                    if (orderItemDTO.getId().equals(orderItemId)) {
                        orderItemService.deleteById(orderItemId);
                        return ResponseEntity.ok("Item deleted successfully");
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You do not have permission to do this action");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete Item");
        }
    }
}
