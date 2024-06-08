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

import com.example.grocery.entities.VegetableDiscount;
import com.example.grocery.services.VegetableDiscountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/vegetable_discounts")
@Tag(name = "Vegetable Discount Controller", description = "API for managing vegetable discounts")
public class VegetableDiscountController {
    private final VegetableDiscountService vegetableDiscountService;

    @Autowired
    public VegetableDiscountController(VegetableDiscountService vegetableDiscountService) {
        this.vegetableDiscountService = vegetableDiscountService;
    }

    @Operation(summary = "Get all vegetable discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all vegetable discounts", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VegetableDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable discounts not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllVegetableDiscount() {
        List<VegetableDiscount> discounts = vegetableDiscountService.findAll();
        if (!discounts.isEmpty()) {
            return ResponseEntity.ok(discounts);
        }
        return new ResponseEntity<>("No vegetable discounts available", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get vegetable discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the vegetable discount", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VegetableDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable discount not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VegetableDiscount> getVegetableDiscountById(@PathVariable("id") Long id) {
        Optional<VegetableDiscount> vegetableDiscount = vegetableDiscountService.findById(id);
        return vegetableDiscount
                .map(vd -> new ResponseEntity<>(vd, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new vegetable discount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vegetable discount created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VegetableDiscount.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createVegetableDiscount(@RequestBody VegetableDiscount vegetableDiscount) {
        try {
            VegetableDiscount saveDiscount = vegetableDiscountService.save(vegetableDiscount);
            return new ResponseEntity<>(saveDiscount, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating a vegetable discount" + e.getMessage());
            return new ResponseEntity<>("Error creating a vegetable discount", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a vegetable discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vegetable discount updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VegetableDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable discount not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VegetableDiscount> updateVegetableDiscount(@PathVariable("id") Long id,
            @RequestBody VegetableDiscount vegetableDiscount) {
        Optional<VegetableDiscount> vdOptional = vegetableDiscountService.findById(id);
        if (vdOptional.isPresent()) {
            VegetableDiscount exDiscount = vdOptional.get();
            exDiscount.setMinGrams(vegetableDiscount.getMinGrams());
            exDiscount.setMaxGrams(vegetableDiscount.getMaxGrams());
            exDiscount.setDiscountPercentage(vegetableDiscount.getDiscountPercentage());
            VegetableDiscount updDiscount = vegetableDiscountService.save(exDiscount);
            return ResponseEntity.ok(updDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Partially update a vegetable discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vegetable discount partially updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = VegetableDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Vegetable discount not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<VegetableDiscount> partialUpdateVegetableDiscount(@PathVariable("id") Long id,
            @RequestBody VegetableDiscount vegetableDiscount) {
        Optional<VegetableDiscount> vdOptional = vegetableDiscountService.findById(id);
        if (vdOptional.isPresent()) {
            VegetableDiscount exDiscount = vdOptional.get();
            if (vegetableDiscount.getMinGrams() != null) {
                exDiscount.setMinGrams(vegetableDiscount.getMinGrams());
            }

            if (vegetableDiscount.getMaxGrams() != null) {
                exDiscount.setMaxGrams(vegetableDiscount.getMaxGrams());
            }

            if (vegetableDiscount.getDiscountPercentage() != null) {
                exDiscount.setDiscountPercentage(vegetableDiscount.getDiscountPercentage());
            }

            VegetableDiscount updVegetableDiscount = vegetableDiscountService.save(exDiscount);
            return ResponseEntity.ok(updVegetableDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a vegetable discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vegetable discount deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vegetable discount not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable("id") Long id) {
        Optional<VegetableDiscount> vdOptional = vegetableDiscountService.findById(id);
        if (vdOptional.isPresent()) {
            vegetableDiscountService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
