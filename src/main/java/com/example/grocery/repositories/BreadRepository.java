package com.example.grocery.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Bread;

public interface BreadRepository extends JpaRepository<Bread, Long>{
    
    Optional<Bread> findByInventoryId(Long inventoryId);
    
}
