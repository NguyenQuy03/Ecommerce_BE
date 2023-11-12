package com.ecommerce.springbootecommerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

public interface IProductItemService {
    void saveAll(ProductDTO productDTO, ProductEntity productEntity);
    void save(ProductItemDTO dto, ProductDTO productDTO, ProductEntity productEntity);
    void update(ProductItemDTO dto, ProductEntity productEntity);

    void filterUnUsedProductItem(ProductDTO productDTO);

    List<ProductItemDTO> findAllByStatus(String stringActiveStatus, Pageable pageable);

    ProductItemDTO findOneById(Long id);

    List<ProductItemDTO> findTopSelling(String sellerName);

}
