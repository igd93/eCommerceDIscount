package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Inventory;

public interface InventoryService {

    List<Inventory> findAll();

    Optional<Inventory> findById(Long id);

    Inventory save(Inventory inventory);

    void deleteById(Long Id);

}
