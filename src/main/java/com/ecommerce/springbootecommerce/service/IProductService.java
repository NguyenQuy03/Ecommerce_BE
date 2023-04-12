package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductService {
    void save(ProductDTO productDTO);
    void save(ProductEntity entity);

    void softDelete(long[] ids);
    void forceDelete(long[] ids);
    
    void restore(long id);
    
    //FIND
    ProductDTO findById(Long id);
    List<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findAllByStatus(String status, Pageable pageable);
    List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable);
    List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable);
    List<ProductDTO> findByStockGreaterThanAndAccountIdAndStatusNot(long stock, Long id, String ignoreStatus, Pageable pageable);
    List<ProductDTO> findByStockEqualsAndAccountIdAndStatusNot(long stock, Long id, String ignoreStatus, Pageable pageable);
    List<ProductDTO> findAllByAccountIdAndStatus(Long id, String status, Pageable pageable);
    List<ProductDTO> findAllByAccountIdAndStatusNot(long id, String ignoreStatus, Pageable pageable);
    List<ProductDTO> findAllBySellerNameOrderBySoldDesc(String sellerName);
    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
    
    //COUNT
    long countAllByAccountId(long accountId);
    long countAllByCategoryId(Long categoryId);
    long countByStockGreaterThanAndAccountIdAndStatusNot(long stock, Long id, String ignoreStatus);
    long countByStockEqualsAndAccountIdAndStatusNot(long stock, Long id, String ignoreStatus);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(long accountId, String status);
    long countAllByAccountIdAndStatusNot(long id, String ignoreStatus);

}
