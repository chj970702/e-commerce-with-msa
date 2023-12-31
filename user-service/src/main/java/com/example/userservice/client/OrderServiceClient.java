package com.example.userservice.client;

import com.example.userservice.vo.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    /**
     * order-service의 controller 호출
     */
    @GetMapping("/order-service/{userId}/orders")
    List<OrderResponse> getOrders(@PathVariable("userId") String userId);
}
