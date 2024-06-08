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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.grocery.entities.Inventory;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory controller", description = "API for managing inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Get all inventory items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all inventory items", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory items are not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllInventory() {
        List<Inventory> inventoryItems = inventoryService.findAll();
        if (!inventoryItems.isEmpty()) {
            return ResponseEntity.ok(inventoryItems);
        }
        return new ResponseEntity<>("Inventory is empty", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get inventory item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the inventory item", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") Long id) {
        Optional<Inventory> inventoryItem = inventoryService.findById(id);
        return inventoryItem
                .map(it -> new ResponseEntity<>(it, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new inventory item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory item created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
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

    @Operation(summary = "Update an inventory item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory item updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
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

    @Operation(summary = "Partially update an inventory item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory item partially updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Inventory.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
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

    @Operation(summary = "Delete an inventory item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inventory item deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
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
