package com.example.grocery.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.entities.CartItem;
import com.example.grocery.services.CartItemService;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<?> getCartItems() {
        List<CartItem> cartItems = cartItemService.findAll();
        if (cartItems.isEmpty()) {
            return ResponseEntity.ok("The shopping cart is empty");
        }
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@RequestParam Long inventoryId, @RequestParam int quantity){
        CartItem cartItem = cartItemService.addCartItem(inventoryId, quantity);
        return ResponseEntity.ok(cartItem);
    }    

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestParam int quantity) {
        CartItem cartItem = cartItemService.updateCartItem(id, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartItemService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }
    
}
