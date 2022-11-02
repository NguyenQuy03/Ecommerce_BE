package com.ecommerce.springbootecommerce.converter;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

@Component
public class ProductConverter {
    
    public ProductEntity toEntity(ProductDTO product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setImage(product.getImage());
        productEntity.setStock(product.getStock());
        productEntity.setDescription(product.getDescription());
        return productEntity;
    }
    
    public ProductDTO toDTO(ProductEntity entity) {
        ProductDTO productDTO = new ProductDTO();
        if (entity.getId() != null) {
            productDTO.setId(entity.getId());
        } else {
            productDTO.setModifiedDate(entity.getMordifiedDate());
            productDTO.setModifiedBy(entity.getMordifiedBy());
        }
        productDTO.setName(entity.getName());
        productDTO.setPrice(entity.getPrice());
        productDTO.setImage(entity.getImage());
        productDTO.setStock(entity.getStock());
        productDTO.setDescription(entity.getDescription());
        
        productDTO.setCreatedDate(entity.getCreatedDate());
        productDTO.setCreatedBy(entity.getCreatedBy());
        
        return productDTO;
    }
    
    public ProductEntity toEntity(ProductDTO product, ProductEntity productEntity) {
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setImage(product.getImage());
        productEntity.setStock(product.getStock());
        productEntity.setDescription(product.getDescription());
        return productEntity;
    }
}
