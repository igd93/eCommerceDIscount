package com.example.grocery.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.CartItem;
import com.example.grocery.entities.Inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class CartItemRepositoryTest {

    private final InventoryRepository inventoryRepository;
    private final CartItemRepository cartItemRepository;

    private Inventory savedInventory;

    @Autowired
    public CartItemRepositoryTest(InventoryRepository inventoryRepository, CartItemRepository cartItemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @BeforeEach
    void setUp() {
        Inventory inventory = new Inventory();
        inventory.setProductType("Beer");
        savedInventory = inventoryRepository.save(inventory);
    }

    @Test
    public void testFindByInventoryId() {
        // Given
        String name = "Dutch";
        CartItem cartItem = new CartItem();
        cartItem.setInventory(savedInventory);
        cartItem.setPrice(BigDecimal.valueOf(11.00));
        cartItem.setProductName(name);
        cartItem.setQuantity(5);

        cartItemRepository.save(cartItem);

        // When
        Optional<CartItem> foundCartItem = cartItemRepository.findByInventoryId(savedInventory.getId());

        // Then
        assertThat(foundCartItem).isPresent();
        assertThat(foundCartItem.get().getInventory().getId()).isEqualTo(savedInventory.getId());
        assertThat(foundCartItem.get().getPrice()).isEqualTo(BigDecimal.valueOf(11.00));
        assertThat(foundCartItem.get().getProductName()).isEqualTo(name);
        assertThat(foundCartItem.get().getQuantity()).isEqualTo(5);

    }

    @Test
    public void testFindByInventoryId_NotFound() {

        // When
        Optional<CartItem> foundCartItem = cartItemRepository.findByInventoryId(999L);

        // Then
        assertThat(foundCartItem).isNotPresent();

    }

}
