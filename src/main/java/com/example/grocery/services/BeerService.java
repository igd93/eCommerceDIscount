package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Beer;

public interface BeerService {

    List<Beer> findAll();

    Optional<Beer> findById(Long id);

    Beer save(Beer beer);

    void deleteById(Long id);
    
}
