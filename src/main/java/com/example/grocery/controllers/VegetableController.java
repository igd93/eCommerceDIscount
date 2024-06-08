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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.grocery.entities.Vegetable;

@RestController
@RequestMapping("/api/vegetables")
@Tag(name = "Vegetable Controller", description = "API for managin vegetables")
public class VegetableController {

    private final VegetableService vegetableService;

    @Autowired
    public VegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }

    @Operation(summary = "Get all vegetables")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all vegetables", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vegetable.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetables not found", content = @Content)
    })
    @GetMapping
    public List<Vegetable> getAllVegetables() {
        return vegetableService.findAll();
    }

    @Operation(summary = "Get vegetable by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the vegetable", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vegetable.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vegetable> getVegetableById(@PathVariable("id") Long id) {
        Optional<Vegetable> vegetable = vegetableService.findById(id);
        return vegetable
                .map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new vegetable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vegetable created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vegetable.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody Vegetable vegetable) {
        try {
            Vegetable saveVegetable = vegetableService.save(vegetable);
            return new ResponseEntity<>(saveVegetable, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating vegetable" + e.getMessage());
            return new ResponseEntity<>("Error creating vegetable", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a vegetable by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vegetable updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vegetable.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable not found", content = @Content)
    })
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

    @Operation(summary = "Partially update a vegetable by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vegetable partially updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vegetable.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Vegetable> partialUpdateVegetable(@PathVariable("id") Long id,
            @RequestBody Vegetable vegetableUpdates) {
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

    @Operation(summary = "Delete a vegetable by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vegetable deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vegetable not found", content = @Content)
    })
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
