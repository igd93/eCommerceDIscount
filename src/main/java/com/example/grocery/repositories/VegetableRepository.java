package com.example.grocery.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Vegetable;

public interface VegetableRepository extends JpaRepository<Vegetable, Long>{
    
    Optional<Vegetable> findByInventoryId(Long inventoryId);
    
} 