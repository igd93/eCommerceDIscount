package com.example.grocery.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entities.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long>{
    Optional<Beer> findByInventoryId(Long inventoryId);    
}
