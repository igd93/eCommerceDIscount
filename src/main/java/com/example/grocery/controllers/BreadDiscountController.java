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

import com.example.grocery.services.BreadDiscountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.grocery.entities.BreadDiscount;

@RestController
@RequestMapping("/api/bread_discounts")
@Tag(name = "Bread Discount Controller", description = "API for managing discounts on bread")
public class BreadDiscountController {

    private final BreadDiscountService breadDiscountService;

    @Autowired
    public BreadDiscountController(BreadDiscountService breadDiscountService) {
        this.breadDiscountService = breadDiscountService;
    }

    @Operation(summary = "Get all bread discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all bread discounts", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BreadDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Discounts not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllBreadDiscounts() {
        List<BreadDiscount> discounts = breadDiscountService.findAll();
        if (!discounts.isEmpty()) {
            return ResponseEntity.ok(discounts);
        }
        return ResponseEntity.ok("No discounts available for bread at the moment");
    }

    @Operation(summary = "Get bread discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the bread discount", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BreadDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Bread discount not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BreadDiscount> getBreadDiscountById(@PathVariable("id") Long id) {
        Optional<BreadDiscount> bdOptional = breadDiscountService.findById(id);
        return bdOptional
                .map(bd -> new ResponseEntity<>(bd, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new bread discount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bread discount created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BreadDiscount.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createBreadDiscount(@RequestBody BreadDiscount breadDiscount) {
        try {
            BreadDiscount saveDiscount = breadDiscountService.save(breadDiscount);
            return new ResponseEntity<>(saveDiscount, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating a quantity discount" + e.getMessage());
            return new ResponseEntity<>("Error creating a Bread Discount", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a bread discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bread discount updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BreadDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Bread not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BreadDiscount> updateBreadDiscount(@PathVariable("id") Long id,
            @RequestBody BreadDiscount breadDiscount) {
        Optional<BreadDiscount> bdOptional = breadDiscountService.findById(id);
        if (bdOptional.isPresent()) {
            BreadDiscount exBreadDiscount = bdOptional.get();
            exBreadDiscount.setMinDaysOld(breadDiscount.getMinDaysOld());
            exBreadDiscount.setMaxDaysOld(breadDiscount.getMaxDaysOld());
            exBreadDiscount.setQuantityMultiplier(breadDiscount.getQuantityMultiplier());
            BreadDiscount updateBreadDiscount = breadDiscountService.save(exBreadDiscount);
            return ResponseEntity.ok(updateBreadDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Partially update a bread discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bread discount partially updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BreadDiscount.class)) }),
            @ApiResponse(responseCode = "404", description = "Bread discount not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BreadDiscount> partialUpdateBreadDiscount(@PathVariable("id") Long id,
            @RequestBody BreadDiscount breadDiscount) {
        Optional<BreadDiscount> bdOptional = breadDiscountService.findById(id);
        if (bdOptional.isPresent()) {
            BreadDiscount existingBreadDiscount = bdOptional.get();

            if (breadDiscount.getMinDaysOld() != null) {
                existingBreadDiscount.setMinDaysOld(breadDiscount.getMinDaysOld());
            }

            if (breadDiscount.getMaxDaysOld() != null) {
                existingBreadDiscount.setMaxDaysOld(breadDiscount.getMaxDaysOld());
            }

            if (breadDiscount.getQuantityMultiplier() != null) {
                existingBreadDiscount.setQuantityMultiplier(breadDiscount.getQuantityMultiplier());
            }
            BreadDiscount updateBreadDiscount = breadDiscountService.save(existingBreadDiscount);
            return ResponseEntity.ok(updateBreadDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a bread discount by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bread discount deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Brad discount not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable("id") Long id) {
        Optional<BreadDiscount> bdOptional = breadDiscountService.findById(id);
        if (bdOptional.isPresent()) {
            breadDiscountService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
