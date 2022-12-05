package com.ecommerce.springbootecommerce.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

@Component
public class ProductConverter {

    public ProductEntity toEntity(ProductDTO product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setDetails(product.getDetails());
        productEntity.setStock(product.getStock());
        productEntity.setImage(product.getImage());
        productEntity.setStatus(product.getStatus());
        return productEntity; 
    }

    public ProductDTO toDTO(ProductEntity entity) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(entity.getId());
        productDTO.setCategoryId(entity.getCategory().getId());
        productDTO.setName(entity.getName());
        productDTO.setPrice(entity.getPrice());
        productDTO.setStock(entity.getStock());
        productDTO.setDescription(entity.getDescription());
        productDTO.setDetails(entity.getDetails());
        productDTO.setImage(entity.getImage());
        productDTO.setStatus(entity.getStatus());

        productDTO.setCreatedDate(entity.getCreatedDate());
        productDTO.setCreatedBy(entity.getCreatedBy());
        productDTO.setModifiedDate(entity.getMordifiedDate());
        productDTO.setModifiedBy(entity.getMordifiedBy());

        return productDTO;
    }

    public ProductEntity toEntity(ProductDTO dto, ProductEntity productEntity) {
        productEntity.setName(dto.getName());
        productEntity.setPrice(dto.getPrice());
        productEntity.setStock(dto.getStock());
        productEntity.setDescription(dto.getDescription());
        productEntity.setImage(dto.getImage());
        productEntity.setStatus(dto.getStatus());
        return productEntity;
    }

    public List<ProductDTO> toListProductDTO(List<ProductEntity> listProductEntity) {
        List<ProductDTO> listProductDTO = new ArrayList<>();
        for (ProductEntity productEntity : listProductEntity) {
            ProductDTO productDTO = toDTO(productEntity);
            listProductDTO.add(productDTO);
        }
        return listProductDTO;
    }
    
}
