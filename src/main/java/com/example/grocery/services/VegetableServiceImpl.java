package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.Vegetable;
import com.example.grocery.repositories.InventoryRepository;
import com.example.grocery.repositories.VegetableRepository;

@Service
public class VegetableServiceImpl implements VegetableService {

    private final VegetableRepository vegetableRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public VegetableServiceImpl(VegetableRepository vegetableRepository, InventoryRepository inventoryRepository) {
        this.vegetableRepository = vegetableRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Vegetable> findAll() {
        return vegetableRepository.findAll();
    }

    @Override
    public Optional<Vegetable> findById(Long id) {
        return vegetableRepository.findById(id);
    }

    @Override
    public Vegetable save(Vegetable vegetable) {
        Inventory inventory = new Inventory();
        inventory.setProductType("Vegetable");
        inventory = inventoryRepository.save(inventory);
        vegetable.setInventory(inventory);
        return vegetableRepository.save(vegetable);
    }

    @Override
    public void deleteById(Long id) {
        vegetableRepository.deleteById(id);
    }

}
