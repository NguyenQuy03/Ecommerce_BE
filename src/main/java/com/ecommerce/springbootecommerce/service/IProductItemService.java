package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;

public interface IProductItemService {

    List<ProductItemDTO> findAllByStatus(String stringActiveStatus, Pageable pageable);

    ProductItemDTO findOneById(Long id);

    List<ProductItemDTO> findTopSelling(String sellerName);
}
