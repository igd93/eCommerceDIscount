package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.entities.Bread;
import com.example.grocery.entities.Inventory;
import com.example.grocery.repositories.BreadRepository;

@Service
public class BreadServiceImpl implements BreadService {

    private final BreadRepository breadRepository;
    private final InventoryService inventoryService;

    @Autowired
    public BreadServiceImpl(BreadRepository breadRepository, InventoryService inventoryService) {
        this.breadRepository = breadRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public List<Bread> findAll() {
        return breadRepository.findAll();
    }

    @Override
    public Optional<Bread> findById(Long id) {
        return breadRepository.findById(id);
    }

    @Override
    public Bread save(Bread bread) {
        Inventory inventory = new Inventory();
        inventory.setProductType("Bread");
        inventory = inventoryService.save(inventory);
        bread.setInventory(inventory);
        return breadRepository.save(bread);
    }

    @Override
    public void deleteById(Long id) {
        breadRepository.deleteById(id);
    }

}
