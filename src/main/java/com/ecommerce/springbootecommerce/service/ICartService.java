package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CartDTO;

public interface ICartService {
    void save(CartDTO cartDTO);
    CartDTO findOneById(String id);

    CartDTO findOneByAccountId(String id);
}
