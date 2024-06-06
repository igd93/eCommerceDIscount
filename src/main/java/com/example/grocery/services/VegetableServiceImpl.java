package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.grocery.entities.Vegetable;
import com.example.grocery.repositories.VegetableRepository;

@Service
public class VegetableServiceImpl implements VegetableService{

    private final VegetableRepository vegetableRepository;

    public VegetableServiceImpl(VegetableRepository vegetableRepository) {
        this.vegetableRepository = vegetableRepository;
    }

    @Override
    public List<Vegetable> findAll() {
        return vegetableRepository.findAll();
    }

    @Override
    public Optional<Vegetable> findById(Long id) {
        return vegetableRepository.findById(id);
    }

    @Override
    public Vegetable save(Vegetable vegetable) {
        return vegetableRepository.save(vegetable);
    }

    @Override
    public void deleteById(Long id) {
        vegetableRepository.deleteById(id);
    }
    
}
