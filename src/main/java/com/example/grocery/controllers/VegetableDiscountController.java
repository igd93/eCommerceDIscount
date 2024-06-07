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

@RestController
@RequestMapping("/api/vegetable_discounts")
public class VegetableDiscountController {
    private final VegetableDiscountService vegetableDiscountService;

    @Autowired
    public VegetableDiscountController(VegetableDiscountService vegetableDiscountService) {
        this.vegetableDiscountService = vegetableDiscountService;
    }

    @GetMapping
    public ResponseEntity<?> getAllVegetableDiscount() {
        List<VegetableDiscount> discounts = vegetableDiscountService.findAll();
        if (!discounts.isEmpty()) {
            return ResponseEntity.ok(discounts);
        }
        return ResponseEntity.ok("Currently there are no discounts for vegetables");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VegetableDiscount> getVegetableDiscountById(@PathVariable("id") Long id) {
        Optional<VegetableDiscount> vegetableDiscount = vegetableDiscountService.findById(id);
        return vegetableDiscount
                .map(vd -> new ResponseEntity<>(vd, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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
