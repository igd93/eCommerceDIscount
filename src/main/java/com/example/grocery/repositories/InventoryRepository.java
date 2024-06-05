package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
}
