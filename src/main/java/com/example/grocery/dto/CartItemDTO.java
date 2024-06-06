package com.example.grocery.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long inventoryId;
    private String productName;
    private String productType;  
    private Integer quantity;
    private BigDecimal price;    
}
