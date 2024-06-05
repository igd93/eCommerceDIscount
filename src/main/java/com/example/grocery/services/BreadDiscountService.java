package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.BreadDiscount;

public interface BreadDiscountService {

    List<BreadDiscount> findAll();

    BreadDiscount findById(Long id);

    BreadDiscount save(BreadDiscount beer);

    void deleteById(Long id);
    
}
