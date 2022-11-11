package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;

public interface IProductService {
    ProductDTO save(ProductDTO productDTO);

    void delete(long[] ids);

    List<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findLiveProduct(Pageable pageable);
    List<ProductDTO> findSoldOutProduct(Pageable pageable);

    long countTotalProduct();
    long countLiveProduct();
    long countSoldOutProduct();


}
