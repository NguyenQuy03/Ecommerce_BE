package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductService {
    void save(ProductDTO productDTO);
    void save(ProductEntity entity);

    void softDelete(String status, long[] ids);
    void forceDelete(String status, long[] ids);
    void restore(long id);
    
    //FIND
    ProductDTO findById(Long id);
    ProductDTO findByAccountIdAndId(long accountId, long id);
    List<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findAllByStatus(String status, Pageable pageable);
    List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable);
    List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable);
    
        //MY PRODUCT CONTROLLER
    List<ProductDTO> findAllByAccountIdAndStatusNotAndStatusNot(long id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    List<ProductDTO> findByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    List<ProductDTO> findByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    
    
    List<ProductDTO> findAllByAccountIdAndStatus(Long id, String status, Pageable pageable);
    List<ProductDTO> findAllBySellerNameOrderBySoldDesc(String sellerName);
    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
    
    //COUNT
    long countAllByAccountId(long accountId);
    long countAllByCategoryId(Long categoryId);
    
        //MY PRODUCT CONTROLLER
    long countAllByAccountIdAndStatusNotAndStatusNot(long id, String ignoreStatus1, String ignoreStatus2);
    long countByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long id, String ignoreStatus1, String ignoreStatus2);
    long countByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long id, String ignoreStatus1, String ignoreStatus2);
    
    
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(long accountId, String status);
    
    //EXIST
    boolean isProductExistByIdAndStatusNot(Long id, String ignoreStatus);

}
