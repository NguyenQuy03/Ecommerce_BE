package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.CartItemRepository;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.AccountConverter;
import com.ecommerce.springbootecommerce.util.converter.OrderConverter;
import com.ecommerce.springbootecommerce.util.converter.OrderItemConverter;
import com.ecommerce.springbootecommerce.util.converter.ProductItemConverter;

@Service
public class OrderService implements IOrderService{
    
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductItemRepository productItemRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private AccountConverter accountConverter;
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private OrderItemConverter orderItemConverter;
    
    @Autowired
    private ProductItemConverter productItemConverter;

    @Override
    public void save(OrderDTO dto) {
        OrderEntity entity = orderConverter.toEntity(dto);
        try {
            try {
                // Save Order
                entity.setAccount(accountConverter.toEntity(dto.getAccount()));
                entity.setStatus(SystemConstant.DELIVERED_STATUS);
                orderRepo.save(entity);
            } catch (Exception e) {
                throw new RuntimeException("Error when save order");
            }

            List<OrderItemDTO> orderItemDTOs = dto.getOrderItems();
    
                // Save Order Item
            for(OrderItemDTO orderItemDTO : orderItemDTOs) {
                OrderItemEntity itemEntity = orderItemConverter.toEntity(orderItemDTO);
                itemEntity.setOrders(entity);
                itemEntity.setProductItem(productItemConverter.toEntity(orderItemDTO.getProductItem()));
                itemEntity.setStatus(SystemConstant.DELIVERED_STATUS);
                
                try {
                    orderItemRepo.save(itemEntity);
                } catch (Exception e) {
                    throw new RuntimeException("Error when save order item");
                }
            }
            
                // Update Product item quantity && stock
            for(OrderItemDTO orderItemDTO : dto.getOrderItems()) {
                ProductItemEntity productItem = productItemRepo.findById(orderItemDTO.getProductItem().getId()).get();
                productItem.decreaseStock(orderItemDTO.getQuantity());
                productItem.increateSold(orderItemDTO.getQuantity());
                productItemRepo.save(productItem);
            }
            
                // Update Cart And Quantity Order
            redisUtil.adjustQuantityOrder(dto.getAccount().getUsername(), -dto.getOrderItems().size());
            cartItemRepo.deleteAllById(dto.getCartItemIds());
        } catch (Exception e) {
            orderRepo.delete(entity);
            throw new RuntimeException("Error occurred, order creation rolled back");
        }
    }
    
        // FIND ONE
    @Override
    public OrderDTO findOneById(Long id) {
        Optional<OrderEntity> entity = orderRepo.findById(id);
        return entity.map(item -> orderConverter.toDTO(item)).orElse(null);
    }
    
    @Override
    public OrderDTO findOneByAccountIdAndStatus(Long accountId, String status) {
        Optional<OrderEntity> entity = orderRepo.findOneByAccountIdAndStatus(accountId, status);
        return entity.map(item -> orderConverter.toDTO(item)).orElse(null);
    }

        // FIND ALL
    @Override
    public List<OrderDTO> findAllByStatus(String status) {
        return orderConverter.toListDTO(orderRepo.findAllByStatus(status));
    }

    @Override
    public List<OrderDTO> findAllByAccountIdAndStatus(Long accountId, String status, int page, int size) {
        Page<OrderEntity> orderPage = orderRepo.findAllByAccountIdAndStatus(accountId, status, PageRequest.of(page - 1, size));
        return orderConverter.toListDTO(orderPage.getContent());
    }

    @Override
    public Long countByAccountIdAndStatus(Long accountId, String status) {
        return orderRepo.countByAccountIdAndStatus(accountId, status);
    }

    @Override
    public boolean isOrderExistByAccountIdAndStatus(Long accountId, String status) {
        return orderRepo.findOneByAccountIdAndStatus(accountId, status).isPresent();
    }
}
