package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.entity.CartItemEntity;
import com.ecommerce.springbootecommerce.repository.CartItemRepository;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.converter.CartItemConverter;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CartItemConverter cartItemConverter;

    @Override
    public void save(CartItemDTO dto) {
        Optional<CartItemEntity> optionalEntity = cartItemRepo.findOneByCartIdAndProductItemId(dto.getCart().getId(), dto.getProductItem().getId());

        if(optionalEntity.isPresent()) {
            CartItemEntity cartItemEntity = optionalEntity.get();
            cartItemEntity.increQuantity(dto.getQuantity());
            cartItemRepo.save(cartItemEntity);
        } else {
            CartItemEntity itemEntity = cartItemConverter.toEntity(dto);
            try {
                cartItemRepo.save(itemEntity);
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Error save cart item");
            }

            redisUtil.adjustQuantityOrder(dto.getUsername(), +1);
        }
    }

    @Override
    public void delete(Long id) {
        cartItemRepo.deleteById(id);
    }

    @Override
    public List<CartItemDTO> findAllByCartId(Long cartId) {
        List<CartItemEntity> listItemEntity = cartItemRepo.findAllByCartId(cartId);
        return toListDTO(listItemEntity);
    }

    @Override
    public CartItemDTO findOneByCartIdAndProductItemId(Long cartId, Long productItemId) {
        Optional<CartItemEntity> entity = cartItemRepo.findOneByCartIdAndProductItemId(cartId, productItemId);
        if(entity.isPresent()){
            return cartItemConverter.toDTO(entity.get());
        }
        return null;
    }

    private List<CartItemDTO> toListDTO(List<CartItemEntity> listItemEntity) {
        List<CartItemDTO> dtos = new ArrayList<>();
        listItemEntity.forEach(entity -> {
            dtos.add(cartItemConverter.toDTO(entity));
        });

        return dtos;
    }

    @Override
    public boolean isExistedByCartIdAndProductItemId(long cartId, long productItemId) {
        Optional<CartItemEntity> entity = cartItemRepo.findOneByCartIdAndProductItemId(cartId, productItemId);
        return entity.isPresent();
    }

    @Override
    public CartItemDTO findOneById(long orderItemId) {
        Optional<CartItemEntity> entity = cartItemRepo.findById(orderItemId);
        if(entity.isPresent()) {
            return cartItemConverter.toDTO(entity.get());
        }
        return null;
    }
}
