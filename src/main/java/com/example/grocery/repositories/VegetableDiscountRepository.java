package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.VegetableDiscount;

public interface VegetableDiscountRepository extends JpaRepository<VegetableDiscount, Long> {
    
}
