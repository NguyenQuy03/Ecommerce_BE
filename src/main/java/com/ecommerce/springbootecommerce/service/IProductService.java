package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductService {
    void save(ProductDTO product);
    void update(ProductDTO product);
    void save(ProductEntity entity);

    void softDelete(String status, String[] ids);
    void forceDelete(String status, String[] ids);
    void restore(String id);
    
        //FIND
    ProductDTO findOneById(String id);
    ProductDTO findByAccountIdAndId(String accountId, String id);
    List<ProductDTO> findAllByStatus(String status, Pageable pageable);
    List<ProductDTO> findAllByCategoryId(String categoryId, Pageable pageable);

    BaseDTO<ProductDTO> findAllByAccountIdAndStatus(String id, String status, int page, int size);
    List<ProductDTO> findTopSelling(String id);
    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
        //MY PRODUCT CONTROLLER
    BaseDTO<ProductDTO> findAllValid(String id, String inactiveProduct, String removedProduct, int page, int size);
    BaseDTO<ProductDTO> findAllLive(String id, String ignoreStatus1, String ignoreStatus2, long stock, int page, int size);
    BaseDTO<ProductDTO> findAllSoldOut(String id, String inactiveProduct, String removedProduct, long stock, int page, int size);

        //COUNT
    long countAllByCategoryId(String categoryId);
    long countByNameContains(String keyword);
    long countAllByStatus(String status);
    long countAllByAccountIdAndStatus(String id, String status);

}
