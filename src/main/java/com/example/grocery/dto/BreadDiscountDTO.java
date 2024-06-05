package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BreadDiscountDTO {    
    private Long id;    
    private Integer minDaysOld;    
    private Integer maxDaysOld;    
    private Integer qunatityMultiplier;    
}
