package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;
import com.example.grocery.repositories.BeerRepository;
import com.example.grocery.repositories.InventoryRepository;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, InventoryRepository inventoryRepository) {
        this.beerRepository = beerRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Optional<Beer> findById(Long id) {
        return beerRepository.findById(id);
    }

    @Override
    public Beer save(Beer beer) {
        Inventory inventory = new Inventory();
        inventory.setProductType("Beer");
        inventory = inventoryRepository.save(inventory);

        beer.setInventory(inventory);
        return beerRepository.save(beer);
    }

    @Override
    public void deleteById(Long id) {
        beerRepository.deleteById(id);
    }

}
