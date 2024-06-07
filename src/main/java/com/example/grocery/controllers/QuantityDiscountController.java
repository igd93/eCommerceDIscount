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

import com.example.grocery.services.QuantityDiscountService;

import com.example.grocery.entities.QuantityDiscount;

import java.util.Optional;

@RestController
@RequestMapping("/api/beer_discounts/")
public class QuantityDiscountController {

    private final QuantityDiscountService quantityDiscountService;

    @Autowired
    public QuantityDiscountController(QuantityDiscountService quantityDiscountService) {
        this.quantityDiscountService = quantityDiscountService;
    }

    @GetMapping
    public ResponseEntity<?> getAllQuantityDiscounts() {
        List<QuantityDiscount> discounts = quantityDiscountService.findAll();
        if (!discounts.isEmpty()) {
            return ResponseEntity.ok(discounts);
        }
        return ResponseEntity.ok("There are currently no beers discounts");
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuantityDiscount> getQuantityDiscountById(@PathVariable("id") Long id) {
        Optional<QuantityDiscount> quantityDiscount = quantityDiscountService.findById(id);
        return quantityDiscount
                .map(qd -> new ResponseEntity<>(qd, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createQuantityDiscount(@RequestBody QuantityDiscount quantityDiscount) {
        try {
            QuantityDiscount saveDiscount = quantityDiscountService.save(quantityDiscount);
            return new ResponseEntity<>(saveDiscount, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error creating a quantity discount" + e.getMessage());
            return new ResponseEntity<>("Error creating a quantityDiscount", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuantityDiscount> updateQuantityDiscount(@PathVariable("id") Long id,
            @RequestBody QuantityDiscount quantityDiscount) {
        Optional<QuantityDiscount> quOptional = quantityDiscountService.findById(id);
        if (quOptional.isPresent()) {
            QuantityDiscount existingDiscount = quOptional.get();
            existingDiscount.setBeer(quantityDiscount.getBeer());
            existingDiscount.setDiscountAmount(quantityDiscount.getDiscountAmount());
            existingDiscount.setInventory(quantityDiscount.getInventory());
            existingDiscount.setQuantity(quantityDiscount.getQuantity());
            QuantityDiscount updateDiscount = quantityDiscountService.save(existingDiscount);
            return ResponseEntity.ok(updateDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuantityDiscount> partialUpdateQuantityDiscount(@PathVariable("id") Long id,
            @RequestBody QuantityDiscount quantityDiscount) {
        Optional<QuantityDiscount> quOptional = quantityDiscountService.findById(id);
        if (quOptional.isPresent()) {
            QuantityDiscount exisitingDiscount = quOptional.get();

            if (quantityDiscount.getBeer() != null) {
                exisitingDiscount.setBeer(quantityDiscount.getBeer());
            }

            if (quantityDiscount.getDiscountAmount() != null) {
                exisitingDiscount.setDiscountAmount(quantityDiscount.getDiscountAmount());
            }

            if (quantityDiscount.getInventory() != null) {
                exisitingDiscount.setInventory(quantityDiscount.getInventory());
            }

            if (quantityDiscount.getQuantity() != null) {
                exisitingDiscount.setQuantity(quantityDiscount.getQuantity());
            }

            QuantityDiscount updateDiscount = quantityDiscountService.save(exisitingDiscount);
            return ResponseEntity.ok(updateDiscount);

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable("id") Long id) {
        Optional<QuantityDiscount> quOptional = quantityDiscountService.findById(id);
        if (quOptional.isPresent()) {
            quantityDiscountService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
