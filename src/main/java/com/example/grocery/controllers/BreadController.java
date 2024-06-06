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

import com.example.grocery.entities.Bread;
import com.example.grocery.services.BreadService;

@RestController
@RequestMapping("/api/beers")
public class BreadController {

    private final BreadService breadService;

    @Autowired
    public BreadController(BreadService breadService) {
        this.breadService = breadService;
    }

    @GetMapping
    public List<Bread> getAllBreads() {
        return breadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bread> getBreadById(@PathVariable("id") Long id) {
        Optional<Bread> bread = breadService.findById(id);
        return bread
                .map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBread(@RequestBody Bread bread) {
        try {
            Bread saveBread = breadService.save(bread);
            return new ResponseEntity<>(saveBread, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating bread" + e.getMessage());
            return new ResponseEntity<>("Error creating bread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bread> updateBread(@PathVariable("id") Long id, @RequestBody Bread bread) {
        Optional<Bread> breadOptional = breadService.findById(id);
        if (breadOptional.isPresent()) {
            Bread existingBread = breadOptional.get();
            existingBread.setProductName(bread.getProductName());
            existingBread.setPricePerUnit(bread.getPricePerUnit());
            Bread updateBread = breadService.save(existingBread);
            return ResponseEntity.ok(updateBread);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Bread> partialUpdateBread(@PathVariable("id") Long id, @RequestBody Bread breadUpdates) {
        Optional<Bread> breadOptional = breadService.findById(id);
        if (breadOptional.isPresent()) {
            Bread existingBread = breadOptional.get();

            if (breadUpdates.getProductName() != null) {
                existingBread.setProductName(breadUpdates.getProductName());
            }

            if (breadUpdates.getPricePerUnit() != null) {
                existingBread.setPricePerUnit(breadUpdates.getPricePerUnit());
            }

            Bread updateBread = breadService.save(existingBread);
            return ResponseEntity.ok(updateBread);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBread(@PathVariable("id") Long id) {
        Optional<Bread> breadOptional = breadService.findById(id);
        if (breadOptional.isPresent()) {
            breadService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
