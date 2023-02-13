package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductService {
    void save(ProductDTO productDTO);

    void delete(long[] ids);
    
    List<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findAllByStatus(String status, Pageable pageable);
    List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable);
    List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable);
    List<ProductDTO> findByStockGreaterThanAndAccountId(long stock, Long id, Pageable pageable);
    List<ProductDTO> findByStockEqualsAndAccountId(long stock, Long id, Pageable pageable);
    
    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
    long countAllByAccountId(long accountId);
    long countAllByCategoryId(Long categoryId);
    long countByStockGreaterThanAndAccountId(long stock, Long id);
    long countByStockEqualsAndAccountId(long stock, Long id);
    long countByNameContains(String keyword);
    long countAllByStatus(String stringActiveStatus);

    ProductDTO findById(Long id);

    void save(ProductEntity product);

    List<ProductDTO> findAllBySellerNameOrderBySoldDesc(String sellerName);

}
