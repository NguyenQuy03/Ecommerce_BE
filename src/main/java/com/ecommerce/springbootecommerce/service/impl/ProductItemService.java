package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.service.IProductItemService;
import com.ecommerce.springbootecommerce.util.converter.ProductItemConverter;

@Service
public class ProductItemService implements IProductItemService {
    @Autowired
    private ProductItemRepository productItemRepo;
    
    @Autowired
    private ProductItemConverter productItemConverter;

    @Override
    public List<ProductItemDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductItemEntity> entities = productItemRepo.findAllByStatus(status, pageable).getContent();
        return productItemConverter.toListDTO(entities);
    }

    @Override
    public ProductItemDTO findOneById(Long id) {
        Optional<ProductItemEntity> entity = productItemRepo.findById(id);
        return entity.map(item -> productItemConverter.toDTO(item)).orElse(null);
    }

    @Override
    public List<ProductItemDTO> findTopSelling(String sellerName) {

        return productItemConverter.toListDTO(productItemRepo.findAllByCreatedByOrderBySoldDesc(sellerName));
    }
}
    