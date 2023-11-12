package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductService {
    void save(ProductDTO product);
    void update(ProductDTO product);
    void save(ProductEntity entity);

    void softDelete(String status, long[] ids);
    void forceDelete(String status, long[] ids);
    void restore(long id);
    
    // FIND
    ProductDTO findById(long id);
    ProductDTO findOneByIdAndAccountId(long id, long accountId);
    List<ProductDTO> findAllByStatus(String status, Pageable pageable);
    List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable);

    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
    List<ProductDTO> findAllByAccountIdAndStatus(long id, String status);

    BaseDTO<ProductDTO> findAllByAccountIdAndStatus(long id, String status, int page, int size);
    BaseDTO<ProductDTO> findAllByAccountIdAndProductStatusAndProductItemStatus(long accountId, String productStatus, String productItemStatus, int page, int size);

    ProductDTO findTopSelling(String sellerName);

    // COUNT
    long countAllByCategoryId(long categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
}
