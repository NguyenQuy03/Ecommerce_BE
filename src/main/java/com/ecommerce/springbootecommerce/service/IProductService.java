package com.ecommerce.springbootecommerce.service;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    List<ProductDTO> findAllByAccountIdAndStatus(String id, String status, Pageable pageable);
    List<ProductDTO> findTopSelling(String id);
    List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable);
    
        //MY PRODUCT CONTROLLER
    List<ProductDTO> findAllValid(String id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    List<ProductDTO> findAllLive(long stock, String id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);
    List<ProductDTO> findAllSoldOut(long stock, String id, String ignoreStatus1, String ignoreStatus2, Pageable pageable);

        //COUNT
    long countAllByCategoryId(String categoryId);
    //MY PRODUCT CONTROLLER
    long countAllValid(String id, String status1, String status2);
    long countAllLive(long stock, String id, String ignoreStatus1, String ignoreStatus2);

    long countAllSoldOut(long stock, String id, String ignoreStatus1, String ignoreStatus2);

    long countByNameContains(String keyword);
    long countAllByStatus(String status);

    long countAllByAccountIdAndStatus(String id, String status);

}
