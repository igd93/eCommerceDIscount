package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.QuantityDiscount;

public interface QuantityDiscountService {

    List<QuantityDiscount> findAll();

    Optional<QuantityDiscount> findById(Long id);

    QuantityDiscount save(QuantityDiscount quantityDiscount);

    void deleteById(Long id);
    
}
