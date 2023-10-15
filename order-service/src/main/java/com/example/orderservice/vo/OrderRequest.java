package com.example.orderservice.vo;

import lombok.Getter;

@Getter
public class OrderRequest {
    private String productId;
    private String qty;
    private Integer unitPrice;
}
