package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.grocery.entities.BreadDiscount;
import com.example.grocery.repositories.BreadDiscountRepository;

@Service
public class BreadDiscountServiceImpl implements BreadDiscountService {

    private final BreadDiscountRepository breadDiscountRepository;

    public BreadDiscountServiceImpl(BreadDiscountRepository breadDiscountRepository) {
        this.breadDiscountRepository = breadDiscountRepository;
    }

    @Override
    public List<BreadDiscount> findAll() {
        return breadDiscountRepository.findAll();
    }

    @Override
    public Optional<BreadDiscount> findById(Long id) {
        return breadDiscountRepository.findById(id);
    }

    @Override
    public BreadDiscount save(BreadDiscount breadDiscount) {
        return breadDiscountRepository.save(breadDiscount);
    }

    @Override
    public void deleteById(Long id) {
        breadDiscountRepository.deleteById(id);
    }

}
