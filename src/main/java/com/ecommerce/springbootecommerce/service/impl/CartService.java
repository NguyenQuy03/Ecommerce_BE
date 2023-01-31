package com.ecommerce.springbootecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.converter.CartConverter;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.entity.CartEntity;
import com.ecommerce.springbootecommerce.repository.CartRepository;
import com.ecommerce.springbootecommerce.service.ICartService;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartConverter cartConverter;

    @Override
    public void save(CartDTO cartDTO) {
        
        if (cartDTO.getId() == null) {
            CartEntity cartEntity = cartConverter.toEntity(cartDTO);
            cartRepository.save(cartEntity);
            
        } else {
            CartEntity preCartEntity = cartRepository.findOneById(cartDTO.getId());
            CartEntity cartEntity = cartConverter.toEntity(cartDTO, preCartEntity);
            cartRepository.save(cartEntity);
            
        }
    }

    @Override
    public CartDTO findByStatusAndAccountId(String status, Long id) {
        CartEntity cartEntity = cartRepository.findOneByStatusAndAccountId(status, id);
        CartDTO cartDTO = cartConverter.toDTO(cartEntity);
        return cartDTO;
    }

    @Override
    public boolean isExistByStatusAndAccountId(String status, Long id) {
        return cartRepository.findByStatusAndAccountId(status, id).isPresent();
    }

    @Override
    public CartDTO findOneById(Long id) {
        CartEntity cartEntity = cartRepository.findOneById(id);
        CartDTO cartDTO = cartConverter.toDTO(cartEntity);
        return cartDTO;
    }
    
    
}
