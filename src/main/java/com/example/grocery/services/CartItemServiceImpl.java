package com.example.grocery.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.grocery.entities.CartItem;
import com.example.grocery.repositories.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);        
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
    
}
