package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.service.IProductItemService;

@Service
public class ProductItemService implements IProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;
    
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ProductItemDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductItemEntity> entities = productItemRepository.findAllByStatus(status, pageable).getContent();
        return toListDTO(entities);
    }

    private List<ProductItemDTO> toListDTO(List<ProductItemEntity> entities) {
        List<ProductItemDTO> dtos = new ArrayList<>();
        entities.forEach(item -> {
            dtos.add(modelMapper.map(item, ProductItemDTO.class));
        });

        return dtos;
    }
}
