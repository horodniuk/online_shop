package com.example.online_shop.service.impl;

import com.example.online_shop.dto.requestDto.OrderRequestDto;
import com.example.online_shop.dto.responseDto.OrderResponseDto;
import com.example.online_shop.entity.Order;
import com.example.online_shop.exception.OrderNotFoundException;
import com.example.online_shop.repository.OrderRepository;
import com.example.online_shop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto findById(Long orderId) {
        Order order = getOrder(orderId);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto saveOrder(Order order) {
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with id " + orderId + " not found!"));
    }

    @Override
    public List<OrderResponseDto> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderResponseDto editOrder(OrderRequestDto orderRequestDto) {
        Order order = getOrder(orderRequestDto.getOrderId());
        order.setOrderDate(orderRequestDto.getOrderDate());
        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional
    @Override
    public OrderResponseDto deleteOrder(Long orderId) {
        Order order = getOrder(orderId);
        orderRepository.delete(order);
        return modelMapper.map(order, OrderResponseDto.class);
    }
}
