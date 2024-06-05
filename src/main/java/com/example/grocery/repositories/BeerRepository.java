package com.example.grocery.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long>{
    Optional<Beer> findByInventoryId(Long inventoryId);    
}
