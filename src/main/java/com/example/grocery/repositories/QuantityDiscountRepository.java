package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.QuantityDiscount;

public interface QuantityDiscountRepository extends JpaRepository<QuantityDiscount, Long>{
    
}
