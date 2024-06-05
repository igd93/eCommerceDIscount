package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.BreadDiscount;

public interface BreadDiscountRepository extends JpaRepository<BreadDiscount, Long>{
    
}
