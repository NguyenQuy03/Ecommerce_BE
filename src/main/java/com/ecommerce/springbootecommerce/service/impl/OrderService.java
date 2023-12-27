package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.ProductItemConverter;
import com.ecommerce.springbootecommerce.util.converter.account_role.AccountConverter;
import com.ecommerce.springbootecommerce.util.converter.order.OrderConverter;
import com.ecommerce.springbootecommerce.util.converter.order.OrderItemConverter;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private ProductItemRepository productItemRepo;

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
    public OrderDTO save(OrderDTO dto) {
        OrderEntity entity = orderConverter.toEntity(dto);
        try {
            entity.setBuyer(accountConverter.toEntity(dto.getBuyer()));
            OrderEntity newEntity = orderRepo.save(entity);

            return orderConverter.toDTO(newEntity);
        } catch (Exception e) {
            orderRepo.delete(entity);
            throw new RuntimeException("Error occurred, order creation rolled back");
        }
    }

    @Override
    public OrderDTO purchase(OrderDTO dto) {
        OrderEntity entity = orderConverter.toEntity(dto);
        try {
            // Save Order
            entity.setBuyer(accountConverter.toEntity(dto.getBuyer()));
            OrderEntity newEntity = orderRepo.save(entity);

            List<OrderItemDTO> orderItemDTOs = dto.getOrderItems();

            // Save Order Item
            for (OrderItemDTO orderItemDTO : orderItemDTOs) {
                OrderItemEntity itemEntity = orderItemConverter.toEntity(orderItemDTO);
                itemEntity.setOrders(entity);
                itemEntity.setProductItem(productItemConverter.toEntity(orderItemDTO.getProductItem()));
                itemEntity.setStatus(OrderStatus.DELIVERED);

                orderItemRepo.save(itemEntity);
            }

            // Update Product item quantity && stock
            for (OrderItemDTO orderItemDTO : dto.getOrderItems()) {
                ProductItemEntity productItem = productItemRepo.findById(orderItemDTO.getProductItem().getId()).get();
                productItem.decreaseStock(orderItemDTO.getQuantity());
                productItem.increateSold(orderItemDTO.getQuantity());
                productItemRepo.save(productItem);
            }

            // Update Cart And Quantity Order
            redisUtil.adjustQuantityOrder(dto.getBuyer().getUsername(), -dto.getOrderItems().size());
            // cartItemRepo.deleteAllById(dto.getCartItemIds());

            return orderConverter.toDTO(newEntity);
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
    public OrderDTO findOneByBuyerIdAndSellerIdAndStatus(Long buyerId, Long sellerId, OrderStatus status) {
        Optional<OrderEntity> orderOptional = orderRepo.findOneByBuyerIdAndSellerIdAndStatus(buyerId, sellerId, status);
        return orderOptional.map(entity -> orderConverter.toDTO(entity)).orElse(null);
    }

    // FIND ALL
    @Override
    public List<OrderDTO> findAllByStatus(OrderStatus status) {
        return orderConverter.toListDTO(orderRepo.findAllByStatus(status));
    }

    @Override
    public List<OrderDTO> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status, int page, int size) {
        Page<OrderEntity> orderPage = orderRepo.findAllByBuyerIdAndStatus(buyerId, status,
                PageRequest.of(page - 1, size));
        return orderConverter.toListDTO(orderPage.getContent());
    }

    @Override
    public List<OrderDTO> findAllByBuyerIdAndStatus(Long buyerId, OrderStatus status) {
        List<OrderEntity> orderEntities = orderRepo.findAllByBuyerIdAndStatus(buyerId, status);
        return orderConverter.toListDTO(orderEntities);
    }

    // COUNT AND CHECK
    @Override
    public Long countByBuyerIdAndStatus(Long buyerId, OrderStatus status) {
        return orderRepo.countByBuyerIdAndStatus(buyerId, status);
    }

}
