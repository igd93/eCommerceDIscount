package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuantityDiscountDTO {
    
    private Long id;   
    private Long inventoryId;    
    private Integer quantity;    
    private Integer discountAMount;
    
}
