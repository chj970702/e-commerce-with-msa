package com.example.catalogservice.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class CatalogDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;
}
