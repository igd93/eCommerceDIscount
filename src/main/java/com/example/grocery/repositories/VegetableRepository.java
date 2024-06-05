package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Vegetable;

public interface VegetableRepository extends JpaRepository<Vegetable, Long>{

    
} 