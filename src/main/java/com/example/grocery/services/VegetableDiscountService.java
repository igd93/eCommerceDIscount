package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.VegetableDiscount;

public interface VegetableDiscountService {
    List<VegetableDiscount> findAll();

    VegetableDiscount findById(Long id);

    VegetableDiscount save(VegetableDiscount vegetableDiscount);

    void deleteById(Long id);
    
}
