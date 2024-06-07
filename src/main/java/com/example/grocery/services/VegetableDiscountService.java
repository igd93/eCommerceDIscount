package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.VegetableDiscount;

public interface VegetableDiscountService {
    List<VegetableDiscount> findAll();

    Optional<VegetableDiscount> findById(Long id);

    VegetableDiscount save(VegetableDiscount vegetableDiscount);

    void deleteById(Long id);

}
