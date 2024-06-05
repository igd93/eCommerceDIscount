package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemDTO {
    private Long id;
    private Long invnetoryId;
    private Integer quantity;
    private Double price;    
}
