package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.service.ICartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(CartDTO cartDTO) {
        
        if (cartDTO.getId() == null) {
            cartRepository.save(modelMapper.map(cartDTO, CartEntity.class));
        } else {
            Optional<CartEntity> cart = cartRepository.findOneById(cartDTO.getId());
            if(Boolean.TRUE.equals(cart.isPresent())) {
                CartEntity preCartEntity = cart.get();
                modelMapper.map(cartDTO, preCartEntity);
                List<OrderEntity> orderEntities = new ArrayList<>();
                for(OrderDTO order : cartDTO.getSetOrders()) {
                    orderEntities.add(modelMapper.map(order, OrderEntity.class));
                }

                preCartEntity.setSetOrders(orderEntities);
                cartRepository.save(modelMapper.map(preCartEntity, CartEntity.class));
            }
        }
    }

    @Override
    public CartDTO findByStatusAndAccountId(String status, String id) {
        Optional<CartEntity> cart = cartRepository.findOneByStatusAndAccountId(status, id);
        return cart.map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class)).orElse(null);
    }

    @Override
    public boolean isExistByStatusAndAccountId(String status, String id) {
        return cartRepository.findOneByStatusAndAccountId(status, id).isPresent();
    }

    @Override
    public CartDTO findOneById(String id) {
        Optional<CartEntity> cart = cartRepository.findOneById(id);
        return cart.map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class)).orElse(null);
    }
}
