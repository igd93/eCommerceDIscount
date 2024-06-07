package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.grocery.entities.QuantityDiscount;
import com.example.grocery.repositories.QuantityDiscountRepository;

@Service
public class QuantityDiscountServiceImpl implements QuantityDiscountService {

    private final QuantityDiscountRepository quantityDiscountRepository;

    public QuantityDiscountServiceImpl(QuantityDiscountRepository quantityDiscountRepository) {
        this.quantityDiscountRepository = quantityDiscountRepository;
    }

    @Override
    public List<QuantityDiscount> findAll() {
        return quantityDiscountRepository.findAll();
    }

    @Override
    public Optional<QuantityDiscount> findById(Long id) {
        return quantityDiscountRepository.findById(id);
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
