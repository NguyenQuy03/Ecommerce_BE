package com.ecommerce.springbootecommerce.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;

@Component
public class ProductItemConverter {
    @Autowired
    private ModelMapper mapper;
    
    public List<ProductItemDTO> toListDTO(List<ProductItemEntity> entities){
        List<ProductItemDTO> dtos = new ArrayList<>();
        for(ProductItemEntity entity : entities) {
            dtos.add(toDTO(entity));
        }
        
        return dtos;
    }
    
    public ProductItemDTO toDTO(ProductItemEntity entity) {
        return mapper.map(entity, ProductItemDTO.class);
    }

    public ProductItemEntity toEntity(ProductItemDTO dto) {
        return mapper.map(dto, ProductItemEntity.class);
    }

    public ProductItemEntity toEntity(ProductItemEntity preEntity, ProductItemDTO dto) {
        ProductItemEntity entity = mapper.map(dto, ProductItemEntity.class);
        entity.setCreatedBy(preEntity.getCreatedBy());
        entity.setCreatedDate(preEntity.getCreatedDate());
        return entity;
    }

    public ProductItemEntity toEntity(ProductItemDTO dto, ProductItemEntity preProductItemEntity) {
        ProductItemEntity preEntity = preProductItemEntity;
        preEntity.setImage(dto.getImage());
        preEntity.setStatus(dto.getStatus());
        preEntity.setStock(dto.getStock());
        preEntity.setPrice(dto.getPrice());
        return preEntity;
    }

    public List<ProductItemEntity> toListEntity(List<ProductItemDTO> dtos){
        List<ProductItemEntity> entities = new ArrayList<>();
        for(ProductItemDTO dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
