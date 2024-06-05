package com.example.grocery.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.grocery.entities.VegetableDiscount;
import com.example.grocery.repositories.VegetableDiscountRepository;

@Service
public class VegetableDiscountServiceImpl implements VegetableDiscountService{

    private final VegetableDiscountRepository vegetableDiscountRepository;

    public VegetableDiscountServiceImpl(VegetableDiscountRepository vegetableDiscountRepository) {
        this.vegetableDiscountRepository = vegetableDiscountRepository;
    }

    @Override
    public List<VegetableDiscount> findAll() {
        return vegetableDiscountRepository.findAll();
    }

    @Override
    public VegetableDiscount findById(Long id) {
        return vegetableDiscountRepository.findById(id).orElse(null);
    }

    @Override
    public VegetableDiscount save(VegetableDiscount vegetableDiscount) {
        return vegetableDiscountRepository.save(vegetableDiscount);                        
    }

    @Override
    public void deleteById(Long id) {
        vegetableDiscountRepository.deleteById(id);
    }
    
}
