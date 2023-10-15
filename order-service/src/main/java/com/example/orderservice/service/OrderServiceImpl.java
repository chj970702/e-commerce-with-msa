package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderRepository;
import com.example.orderservice.vo.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Order order = mapper.map(orderDto, Order.class);

        orderRepository.save(order);

        OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        OrderResponse orderResponse = new ModelMapper().map(order, OrderResponse.class);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrders(String userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(order -> {
            orderResponses.add(new ModelMapper().map(order, OrderResponse.class));
        });

        return orderResponses;
    }
}
