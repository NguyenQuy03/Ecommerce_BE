package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.dto.CartItemDTO;

public interface ICartItemService {

    void save(CartItemDTO cartItem);

    void delete(Long id);

    CartItemDTO findOneByCartIdAndProductItemId(Long cartId, Long productItemId);
    
    CartItemDTO findOneById(long orderItemId);
    
    List<CartItemDTO> findAllByCartId(Long id);

    boolean isExistedByCartIdAndProductItemId(long cartId, long productItemId);

}
