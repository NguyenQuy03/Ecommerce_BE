package com.ecommerce.springbootecommerce.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.entity.VoucherEntity;

@Component
public class VoucherConverter {

    @Autowired
    private ModelMapper mapper;
    
    public VoucherEntity toEntity(VoucherDTO dto) {
        VoucherEntity entity = mapper.map(dto, VoucherEntity.class);

        return entity;
    }

    public VoucherDTO toDTO(VoucherEntity entity) {
        VoucherDTO dto = mapper.map(entity, VoucherDTO.class);

        return dto;
    }

    public List<VoucherEntity> toListEntity(List<VoucherDTO> dtos) {
        List<VoucherEntity> entities = new ArrayList<>();
        for(VoucherDTO dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }

    public List<VoucherDTO> toListDTO(List<VoucherEntity> entities) {
        List<VoucherDTO> dtos = new ArrayList<>();
        for(VoucherEntity entity : entities) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }

    public VoucherEntity toEntity(VoucherDTO dto, VoucherEntity preEntity) {
        dto.setCreatedBy(preEntity.getCreatedBy());
        dto.setCreatedDate(preEntity.getCreatedDate());

        return mapper.map(dto, preEntity.getClass());
    }
}
