package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CartDTO;

public interface ICartService {
    void save(CartDTO cartDTO);
    
    void delete(Long orderItemId, Long cartId);

    CartDTO findOneById(Long id);

    CartDTO findOneByAccountId(Long id);
}
