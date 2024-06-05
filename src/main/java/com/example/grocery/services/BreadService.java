package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.Bread;

public interface BreadService {

    List<Bread> findAll();

    Bread findById(Long id);

    Bread save(Bread beer);

    void deleteById(Long id);
} 