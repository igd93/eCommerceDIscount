package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.Beer;

public interface BeerService {

    List<Beer> findAll();

    Beer findById(Long id);

    Beer save(Beer beer);

    void deleteById(Long id);
    
}
