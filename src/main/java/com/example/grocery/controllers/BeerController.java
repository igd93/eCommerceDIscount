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

import com.example.grocery.entities.Beer;
import com.example.grocery.services.BeerService;



@RestController
@RequestMapping("/api/beers")
public class BeerController {

    
    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService){
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> getAllBeers() {
        return beerService.findAll();        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id") Long id) {
        Optional<Beer> beer = beerService.findById(id);
        return beer
        .map(b -> new ResponseEntity<>(b, HttpStatus.OK))
        .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody Beer beer) {
        try{
        Beer saveBeer = beerService.save(beer);
        return new ResponseEntity<>(saveBeer, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating beer" + e.getMessage());
            return new ResponseEntity<>("Error creating bread", HttpStatus.INTERNAL_SERVER_ERROR);            
        }
        
    }

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
