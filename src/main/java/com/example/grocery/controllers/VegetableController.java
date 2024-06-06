package com.example.grocery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.grocery.services.VegetableService;

import com.example.grocery.entities.Vegetable;

@RestController
@RequestMapping("/api/vegetables")
public class VegetableController {

    private final VegetableService vegetableService;

    @Autowired
    public VegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }

    @GetMapping
    public List<Vegetable> getAllVegetables() {
        return vegetableService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vegetable> getVegetableById(@PathVariable("id") Long id) {
        Optional<Vegetable> vegetable = vegetableService.findById(id);
        return vegetable
                .map(v-> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody Vegetable vegetable) {
        try{
            Vegetable saveVegetable = vegetableService.save(vegetable);
            return new ResponseEntity<>(saveVegetable, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating vegetable" + e.getMessage());
            return new ResponseEntity<>("Error creating vegetable", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vegetable> updateVegetable(@PathVariable("id") Long id, @RequestBody Vegetable vegetable) {
        Optional<Vegetable> vegOptional = vegetableService.findById(id);
        if (vegOptional.isPresent()) {
            Vegetable existingVegetable = vegOptional.get();
            existingVegetable.setProductName(vegetable.getProductName());
            existingVegetable.setPricePer100g(vegetable.getPricePer100g());
            Vegetable updateVegetable = vegetableService.save(existingVegetable);
            return ResponseEntity.ok(updateVegetable);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vegetable> partialUpdateVegetable(@PathVariable("id") Long id, @RequestBody Vegetable vegetableUpdates) {
        Optional<Vegetable> vegOptional = vegetableService.findById(id);
        if (vegOptional.isPresent()) {
            Vegetable existingVegetable = vegOptional.get();

            if (vegetableUpdates.getProductName() != null) {
                existingVegetable.setProductName(vegetableUpdates.getProductName());
            }

            if (vegetableUpdates.getPricePer100g() != null) {
                existingVegetable.setPricePer100g(vegetableUpdates.getPricePer100g());                
            }

            Vegetable updateVegetable = vegetableService.save(existingVegetable);
            return ResponseEntity.ok(updateVegetable);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVegetable(@PathVariable("id") Long id) {
        Optional<Vegetable> vegOptional = vegetableService.findById(id);
        if (vegOptional.isPresent()) {
            vegetableService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
