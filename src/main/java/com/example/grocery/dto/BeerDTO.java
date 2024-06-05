package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BeerDTO {
    
    private Long id;    
    private String productName;    
    private Double pricePerUnit;    
    private Boolean isPack;    
    private Long inventoryId;
    
}
