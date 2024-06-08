package com.example.grocery.controllers;

import java.util.List;

import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocery.dto.CartItemDTO;
import com.example.grocery.services.CartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "CartItem Controller", description = "API for managing the shopping cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Operation(summary = "Get all shopping cart items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all cart items", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Shopping cart is empty", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getCartItems() {
        List<CartItemDTO> cartItems = cartItemService.findAll();
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>("Shopping cart is empty", HttpStatus.NOT_FOUND);
        }
        BigDecimal totalAmount = cartItems.stream()
                .map(CartItemDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartItems);
        response.put("totalAmount", totalAmount);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new item to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItem(@RequestParam Long inventoryId, @RequestParam int quantity) {
        CartItemDTO cartItemDTO = cartItemService.addCartItem(inventoryId, quantity);
        return ResponseEntity.ok(cartItemDTO);
    }

    @Operation(summary = "Update an item cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestParam int quantity) {
        CartItemDTO cartItemDTO = cartItemService.updateCartItem(id, quantity);
        return ResponseEntity.ok(cartItemDTO);
    }

    @Operation(summary = "Delete an item from the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart Item deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart Item not found", content = @Content)
    })
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartItemService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }

}
