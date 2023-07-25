package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CartDTO;

public interface ICartService {
    void save(CartDTO cartDTO);
    boolean isExistByStatusAndAccountId(String status, String id);
    CartDTO findByStatusAndAccountId(String status, String id);
    CartDTO findOneById(String id);
}
