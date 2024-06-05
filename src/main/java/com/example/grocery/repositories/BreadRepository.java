package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.Bread;

public interface BreadRepository extends JpaRepository<Bread, Long>{
    
}
