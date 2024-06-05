package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VegetableDiscountDTO {    
    private Long id;    
    private Integer minGrams;    
    private Integer maxGrams;
    private Double discountPercentage;    
}
