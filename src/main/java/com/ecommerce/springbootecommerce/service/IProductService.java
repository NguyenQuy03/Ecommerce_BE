package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;

public interface IProductService {
    ProductDTO save(ProductDTO productDTO);

    void delete(long[] ids);

    List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable);
    List<ProductDTO> findLiveProduct(Pageable pageable);
    List<ProductDTO> findSoldOutProduct(Pageable pageable);

    long countAllByAccountId(long accountId);
    long countLiveProduct();
    long countSoldOutProduct();

    List<ProductDTO> findAll(Pageable pageable);
}
