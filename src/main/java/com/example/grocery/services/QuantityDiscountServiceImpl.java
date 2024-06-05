package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.QuantityDiscount;
import com.example.grocery.repositories.QuantityDiscountRepository;

public class QuantityDiscountServiceImpl implements QuantityDiscountService{

    private final QuantityDiscountRepository quantityDiscountRepository;

    public QuantityDiscountServiceImpl(QuantityDiscountRepository quantityDiscountRepository) {
        this.quantityDiscountRepository = quantityDiscountRepository;
    }

    @Override
    public List<QuantityDiscount> findAll() {
        return quantityDiscountRepository.findAll();
    }

    @Override
    public QuantityDiscount findById(Long id) {
        return quantityDiscountRepository.findById(id).orElse(null);
    }

    @Override
    public QuantityDiscount save(QuantityDiscount quantityDiscount) {
        return quantityDiscountRepository.save(quantityDiscount);
    }

    @Override
    public void deleteById(Long id) {
        quantityDiscountRepository.deleteById(id);
    }
    
}
