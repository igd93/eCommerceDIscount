package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.Inventory;

public interface InventoryService {

    List<Inventory> findAll();

    Inventory findById(Long id);

    Inventory save(Inventory inventory);

    void deleteById(Long Id);

}
