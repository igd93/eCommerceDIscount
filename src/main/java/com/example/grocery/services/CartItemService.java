package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.CartItem;

public interface CartItemService {

    List<CartItem> findAll();

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);

    void deleteById(Long id);
    
}
