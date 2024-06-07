package com.example.grocery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.grocery.entities.Beer;
import com.example.grocery.services.BeerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/beers")
@Tag(name = "Beer Controller", description = "API for managing beers")
public class BeerController {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @Operation(summary = "Get all beers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all beers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Beer.class)) }),
            @ApiResponse(responseCode = "404", description = "Beers not found", content = @Content)
    })
    @GetMapping
    public List<Beer> getAllBeers() {
        return beerService.findAll();
    }

    @Operation(summary = "Get beer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the beer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Beer.class)) }),
            @ApiResponse(responseCode = "404", description = "Beer not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id") Long id) {
        Optional<Beer> beer = beerService.findById(id);
        return beer
                .map(b -> new ResponseEntity<>(b, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beer created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Beer.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody Beer beer) {
        try {
            Beer saveBeer = beerService.save(beer);
            return new ResponseEntity<>(saveBeer, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating beer" + e.getMessage());
            return new ResponseEntity<>("Error creating beer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a beer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Beer.class)) }),
            @ApiResponse(responseCode = "404", description = "Beer not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Beer> updateBeer(@PathVariable("id") Long id, @RequestBody Beer beer) {
        Optional<Beer> beerOptional = beerService.findById(id);
        if (beerOptional.isPresent()) {
            Beer existingBeer = beerOptional.get();
            existingBeer.setProductName(beer.getProductName());
            existingBeer.setPricePerUnit(beer.getPricePerUnit());
            Beer updateBeer = beerService.save(existingBeer);
            return ResponseEntity.ok(updateBeer);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Partially update a beer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer partially updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Beer.class)) }),
            @ApiResponse(responseCode = "404", description = "Beer not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Beer> partialUpdateBeer(@PathVariable("id") Long id, @RequestBody Beer beerUpdates) {
        Optional<Beer> beerOptional = beerService.findById(id);
        if (beerOptional.isPresent()) {
            Beer existingBeer = beerOptional.get();
            if (beerUpdates.getProductName() != null) {
                existingBeer.setProductName(beerUpdates.getProductName());
            }
            if (beerUpdates.getPricePerUnit() != null) {
                existingBeer.setPricePerUnit(beerUpdates.getPricePerUnit());
            }
            Beer updateBeer = beerService.save(existingBeer);
            return ResponseEntity.ok(updateBeer);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a beer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Beer deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Beer not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable("id") Long id) {
        Optional<Beer> beerOptional = beerService.findById(id);
        if (beerOptional.isPresent()) {
            beerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
