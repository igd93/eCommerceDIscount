package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Vegetable;

public interface VegetableService {

    List<Vegetable> findAll();

    Optional<Vegetable> findById(Long id);

    Vegetable save(Vegetable inventory);

    void deleteById(Long Id);

}
