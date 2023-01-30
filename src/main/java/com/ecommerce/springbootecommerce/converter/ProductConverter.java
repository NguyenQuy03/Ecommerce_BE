package com.ecommerce.springbootecommerce.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

@Component
public class ProductConverter {

    public ProductEntity toEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        entity.setDetails(dto.getDetails());
        entity.setStock(dto.getStock());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        return entity; 
    }

    public ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setDescription(entity.getDescription());
        dto.setDetails(entity.getDetails());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());

        return dto;
    }

    public ProductEntity toEntity(ProductDTO dto, ProductEntity entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        entity.setStatus(dto.getStatus());
        return entity;
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
