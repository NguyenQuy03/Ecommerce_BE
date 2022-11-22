package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;

public interface IProductService {
    ProductDTO save(ProductDTO productDTO);

    void delete(long[] ids);

    List<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable);
    List<ProductDTO> findByStockGreaterThan(Integer stock, Pageable pageable);
    List<ProductDTO> findByStockEquals(Integer stock, Pageable pageable);
    
    long countAllByAccountId(long accountId);
    long countByStockGreaterThan(Integer stock);
    long countByStockEquals(Integer stock);

}
