package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.entity.CartItemEntity;
import com.ecommerce.springbootecommerce.repository.CartItemRepository;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(CartItemDTO cartItem) {
        cartItemRepository.save(modelMapper.map(cartItem, CartItemEntity.class));
    }

    @Override
    public void delete(String id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItemDTO> findAllByCartId(String cartId) {
        List<CartItemEntity> listItemEntity = cartItemRepository.findAllByCartId(cartId);
        return toListDTO(listItemEntity);
    }

    private List<CartItemDTO> toListDTO(List<CartItemEntity> listItemEntity) {
        List<CartItemDTO> dtos = new ArrayList<>();
        listItemEntity.forEach(entity -> {
            dtos.add(modelMapper.map(entity, CartItemDTO.class));
        });

        return dtos;
    }
}
