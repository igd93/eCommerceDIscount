package com.example.grocery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BreadDTO {    
    private Long id;    
    private String productName;   
    private Double pricePerUnit;    
    private Integer age;    
    private Long inventoryId;
    
}
