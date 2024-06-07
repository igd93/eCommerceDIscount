package com.example.grocery.controllers;

import java.util.List;

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

import com.example.grocery.services.InventoryService;
import com.example.grocery.entities.Inventory;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllInventory() {
        List<Inventory> inventoryItems = inventoryService.findAll();
        if (!inventoryItems.isEmpty()) {
            return ResponseEntity.ok(inventoryItems);
        }
        return ResponseEntity.ok("There are currently no items in the inventory");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") Long id) {
        Optional<Inventory> inventoryItem = inventoryService.findById(id);
        return inventoryItem
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createInventoryItem(@RequestBody Inventory inventory) {
        try {
            Inventory saveInventory = inventoryService.save(inventory);
            return new ResponseEntity<>(saveInventory, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating an inventory item" + e.getMessage());
            return new ResponseEntity<>("Error creating a quantityDiscount", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable("id") Long id, @RequestBody Inventory inventory) {
        Optional<Inventory> inOptional = inventoryService.findById(id);
        if (inOptional.isPresent()) {
            Inventory exisitingInventory = inOptional.get();
            exisitingInventory.setProductType(inventory.getProductType());
            Inventory updaInventory = inventoryService.save(exisitingInventory);
            return ResponseEntity.ok(updaInventory);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> partialUpdateInventory(@PathVariable("id") Long id,
            @RequestBody Inventory inventory) {
        Optional<Inventory> inOptional = inventoryService.findById(id);
        if (inOptional.isPresent()) {
            Inventory existingInventory = inOptional.get();
            if (inventory.getProductType() != null) {
                existingInventory.setProductType(inventory.getProductType());
            }
            Inventory updateInventory = inventoryService.save(existingInventory);
            return ResponseEntity.ok(updateInventory);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable("id") Long id) {
        Optional<Inventory> ivOptional = inventoryService.findById(id);
        if (ivOptional.isPresent()) {
            inventoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
