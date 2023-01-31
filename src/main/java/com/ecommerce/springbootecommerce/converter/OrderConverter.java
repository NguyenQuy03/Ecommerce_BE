package com.ecommerce.springbootecommerce.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;

@Component
public class OrderConverter {
    
    public OrderEntity toEntity(OrderDTO dto) {
        OrderEntity entity = new OrderEntity();
        
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setStatus(dto.getStatus());
        entity.setProduct(dto.getProduct());
        entity.setCart(dto.getCart());
        
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        
        return entity;
    }
    
    public OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        dto.setProduct(entity.getProduct());
        dto.setCart(entity.getCart());
        
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        
        return dto;
    }
    
    public List<OrderDTO> toListOrderDTO(List<OrderEntity> listOrderEntity) {
        List<OrderDTO> listOrderDTO = new ArrayList<>();
        for (OrderEntity orderEntity : listOrderEntity) {
            OrderDTO orderDTO = toDTO(orderEntity);
            listOrderDTO.add(orderDTO);
        }
        return listOrderDTO;
    }
}
