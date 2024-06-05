package com.example.grocery.services;

import java.util.List;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Bread;
import com.example.grocery.entities.CartItem;
import com.example.grocery.entities.Vegetable;
import java.math.BigDecimal;

public interface CartItemService {

    CartItem addCartItem(Long inventoryId, int quantity);

    CartItem updateCartItem(Long id, int quantity);

    void applyBeerDiscount(CartItem cartItem, Beer beer);

    void applyBreadDiscount(CartItem cartItem, Bread bread);

    void applyVegetableDiscount(CartItem cartItem, Vegetable vegetable);

    BigDecimal calculateTotalAmount(List<CartItem> cartItems);

    List<CartItem> findAll();

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);

    void removeCartItem(Long id);
    
}
