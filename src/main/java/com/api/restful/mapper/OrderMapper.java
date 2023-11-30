package com.api.restful.mapper;

import com.api.restful.entities.Order;
import com.api.restful.DTO.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO converterToDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order converterToEntity(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, Order.class);
    }
}
