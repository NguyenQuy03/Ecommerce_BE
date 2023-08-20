package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CartItemDTO;

import java.util.List;

public interface ICartItemService {

    void save(CartItemDTO cartItem);

    void delete(String id);

    List<CartItemDTO> findAllByCartId(String id);
}
