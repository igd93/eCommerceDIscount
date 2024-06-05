package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocery.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
} 
