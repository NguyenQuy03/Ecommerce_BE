package com.ecommerce.springbootecommerce.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.repository.OrderRepository;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.CartConverter;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CartConverter cartConverter;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(CartDTO cartDTO) {
        cartRepo.save(modelMapper.map(cartDTO, CartEntity.class));
    }

    @Override
    public CartDTO findOneById(Long id) {
        CartEntity cart = cartRepo.findOneById(id)
            .orElseThrow(() -> new RuntimeException("Cart is not exist"));

        return cartConverter.toDTO(cart);
    }

    @Override
    public CartDTO findOneByAccountId(Long accountId) {
        Optional<CartEntity> cart = cartRepo.findOneByAccountId(accountId);
        return cart.map(cartEntity -> modelMapper.map(cartEntity, CartDTO.class)).orElse(null);
    }

    @Override
    public void delete(Long orderItemId, Long cartId) {
        CartEntity cartEntity = cartRepo.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID: " + cartId));

        // AccountEntity accountEntity = accountRepo.findById(cartId)
        //         .orElseThrow(() -> new NoSuchElementException("Account not found with ID: " + cartItemId));

                
        // OrderEntity orderEntity = orderRepo.findOneByCartIdAndAccountId(cartId, cartId)
        //         .orElseThrow(() -> new NoSuchElementException("Order not found with ID: " + cartItemId));

        cartRepo.save(cartEntity);

        // redisUtil.adjustQuantityOrder(username, -1);
    }
}
