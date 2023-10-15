package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.vo.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderDto orderDto);

    OrderResponse getOrder(String orderId);

    List<OrderResponse> getOrders(String userId);
}
