package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
}
