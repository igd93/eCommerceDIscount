package com.example.grocery.services;

import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Bread;

public interface BreadService {

    List<Bread> findAll();

    Optional<Bread> findById(Long id);

    Bread save(Bread beer);

    void deleteById(Long id);
} 