package com.example.grocery.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entities.QuantityDiscount;

@Repository
public interface QuantityDiscountRepository extends JpaRepository<QuantityDiscount, Long>{
    
    
    Optional<QuantityDiscount> findByBeerId(Long beerId);
    
}
