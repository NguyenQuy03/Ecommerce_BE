package com.ecommerce.springbootecommerce.util;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterUtil {
    public ProductDTO toProductDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setAccountId(entity.getAccountId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setSold(entity.getSold());
        dto.setDescription(entity.getDescription());
        dto.setSpecifications(entity.getSpecifications());

        dto.setDetails(entity.getDetails());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());

        return dto;
    }
}
