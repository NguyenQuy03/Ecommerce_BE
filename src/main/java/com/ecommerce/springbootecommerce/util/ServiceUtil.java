package com.ecommerce.springbootecommerce.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.BaseDTO;

@Component
public class ServiceUtil {
    
    public <T> BaseDTO<T> mapDataFromPage(Page<?> pageEntity) {
        BaseDTO<T> dto = new BaseDTO<>();
        dto.setTotalItem(pageEntity.getTotalElements());
        dto.setTotalPage(pageEntity.getTotalPages());
        dto.setPage((int) pageEntity.getPageable().getPageNumber() + 1);
        dto.setSize(pageEntity.getPageable().getPageSize());
        return dto;
    }
}
