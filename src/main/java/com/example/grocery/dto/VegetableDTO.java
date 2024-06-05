package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VegetableDTO {    
    private Long id;    
    private String productName;    
    private Double pricePer100g;   
    private Long inventoryId;    
}
