package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.OrderInfoRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDto findById(Long orderId);

    OrderResponseDto saveOrder(Order order);

    Order getOrder(Long orderId);

    List<OrderResponseDto> findAll();

    OrderResponseDto editOrder(OrderInfoRequestDto orderRequestDto);

    OrderResponseDto deleteOrder(Long orderId);


}
