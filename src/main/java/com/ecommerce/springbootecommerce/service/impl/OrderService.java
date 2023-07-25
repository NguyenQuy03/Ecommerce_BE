package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(OrderDTO dto) {
        OrderEntity newOrder = orderRepository.save(modelMapper.map(dto, OrderEntity.class));
        CartEntity cart = modelMapper.map(dto.getCart(), CartEntity.class);
        List<OrderEntity> orders = cart.getSetOrders();
        if(orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(newOrder);
        cart.setSetOrders(orders);
        cartRepository.save(cart);

        if(dto.getId() == null) {
            redisUtil.setQuantityOrder(1);
        }
    }

    @Override
    public void purchase(OrderDTO dto) {
        OrderEntity order = orderRepository.findOneById(dto.getId()).get();
        order.setQuantity(dto.getQuantity());
        order.setStatus(SystemConstant.STRING_DELIVERED_ORDER);
        orderRepository.save(modelMapper.map(order, OrderEntity.class));

        order.getProduct().setSold(order.getProduct().getSold() + order.getQuantity());
        long remainingStock = order.getProduct().getStock() - order.getQuantity();
        if (remainingStock == 0) {
            order.getProduct().setStatus(SystemConstant.SOLD_OUT_PRODUCT);
        }
        order.getProduct().setStock(remainingStock);
        productRepository.save(modelMapper.map( order.getProduct(), ProductEntity.class));

        redisUtil.setQuantityOrder(-1);
    }


    @Override
    public void delete(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CartEntity cart = cartRepository.findByStatusAndAccountUsername(SystemConstant.STRING_ACTIVE_STATUS, username).get();

        cart.getSetOrders().removeIf(order -> order.getId().equals(id));
        cartRepository.save(cart);
        orderRepository.deleteById(id);
        redisUtil.setQuantityOrder(-1);
    }
    
    @Override
    public OrderDTO findOneById(String id) {
        Optional<OrderEntity> orderEntity = orderRepository.findOneById(id);
        return orderEntity.map(entity -> modelMapper.map(entity, OrderDTO.class)).orElse(null);
    }
    
    @Override
    public OrderDTO findOneByProductIdAndCartIdAndStatus(String productId, String cartId, String status) {
        Optional<OrderEntity> orderEntity = orderRepository.findOneByProductIdAndCartIdAndStatus(productId, cartId, status);
        return orderEntity.map(entity -> modelMapper.map(entity, OrderDTO.class)).orElse(null);
    }

    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(String cartId, String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status, pageable).getContent();
        return toListDTO(orderEntities);
    }
    
    @Override
    public List<OrderDTO> findAllByCartIdAndStatus(String cartId, String status) {
        List<OrderEntity> orderEntities = orderRepository.findAllByCartIdAndStatus(cartId, status);
        return toListDTO(orderEntities);
    }

    @Override
    public List<OrderDTO> findAllByStatus(String status, Pageable pageable) {
        List<OrderEntity> orderEntities = orderRepository.findAllByStatus(status, pageable).getContent();
        return toListDTO(orderEntities);
    }

    @Override
    public List<OrderDTO> findAllByStatusAndAccountId(String status, String accountId) {

        LookupOperation lookupProduct = LookupOperation.newLookup()
                .from("PRODUCT")
                .localField("product.id")
                .foreignField("_id")
                .as("orders");


        MatchOperation match = Aggregation.match(Criteria.where("product.accountId").is(accountId).and("status").is(status));

        TypedAggregation<OrderEntity> aggregation = Aggregation.newAggregation(OrderEntity.class,
                lookupProduct, match);

        List<OrderEntity> results = mongoTemplate.aggregate(aggregation, OrderEntity.class).getMappedResults();
        return toListDTO(results);
    }

    @Override
    public Long countByCartIdAndStatus(String cartId, String status) {
        return orderRepository.countByCartIdAndStatus(cartId, status);
    }

    @Override
    public boolean isOrderExistByProductIdAndCartIdAndStatus(String productID, String cartId, String status) {
        return orderRepository.findOneByProductIdAndCartIdAndStatus(productID, cartId, status).isPresent();
    }

    private List<OrderDTO> toListDTO(List<OrderEntity> orderEntities) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(OrderEntity entity : orderEntities) {
            orderDTOS.add(modelMapper.map(entity, OrderDTO.class));
        }
        return orderDTOS;
    }
}
