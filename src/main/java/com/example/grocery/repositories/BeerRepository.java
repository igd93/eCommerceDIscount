package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long>{
    
}
