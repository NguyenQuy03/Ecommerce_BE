package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.CartDTO;

public interface ICartService {
    void save(CartDTO cartDTO);
    boolean isExistByStatusAndAccountId(String status, Long id);
    CartDTO findByStatusAndAccountId(String stringActiveStatus, Long id);
}
