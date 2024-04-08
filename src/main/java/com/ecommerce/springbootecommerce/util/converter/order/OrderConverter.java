package com.ecommerce.springbootecommerce.util.converter.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.entity.OrderEntity;
import com.ecommerce.springbootecommerce.entity.OrderItemEntity;
import com.ecommerce.springbootecommerce.repository.OrderItemRepository;
import com.ecommerce.springbootecommerce.util.converter.CurrencyFormater;

@Component
public class OrderConverter {

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private OrderItemConverter orderItemConverter;

    @Autowired
    private ModelMapper mapper;

    public OrderEntity toEntity(OrderDTO dto) {
        OrderEntity entity = mapper.map(dto, OrderEntity.class);

        return entity;
    }

    public OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = mapper.map(entity, OrderDTO.class);

        return dto;
    }

    public List<OrderDTO> toListDTO(List<OrderEntity> orderEntities) {
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (OrderEntity entity : orderEntities) {
            List<OrderItemEntity> orderItemEntities = orderItemRepo.findAllByOrdersIdAndStatus(entity.getId(),
                    OrderStatus.PENDING);
            OrderDTO dto = toDTO(entity);
            dto.setOrderItems(orderItemConverter.toListDTO(orderItemEntities));

            AtomicReference<Double> totalPrice = new AtomicReference<>(0D);

            orderItemEntities.forEach((item) -> {
                totalPrice.accumulateAndGet(new BigDecimal(item.getCurPrice()).doubleValue(),
                        (a, b) -> (double) Double.sum(a.intValue(), b.intValue()));
            });

            dto.setTotalPrice(CurrencyFormater.formatDollars(totalPrice.get()));
            orderDTOs.add(dto);
        }

        return orderDTOs;
    }
}
