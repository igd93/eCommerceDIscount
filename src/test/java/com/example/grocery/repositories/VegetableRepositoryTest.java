package com.example.grocery.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.Vegetable;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class VegetableRepositoryTest {

    private final VegetableRepository vegetableRepository;
    private final InventoryRepository inventoryRepository;

    private Inventory savedInventory;

    @Autowired
    public VegetableRepositoryTest(VegetableRepository vegetableRepository, InventoryRepository inventoryRepository) {
        this.vegetableRepository = vegetableRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @BeforeEach
    void setUp() {
        Inventory inventory = new Inventory();
        inventory.setProductType("Vegetable");
        savedInventory = inventoryRepository.save(inventory);
    }

    @Test
    public void testFindByInventoryId() {
        Vegetable vegetable = new Vegetable();
        String name = "Test Vegetable";
        vegetable.setInventory(savedInventory);
        vegetable.setPricePer100g(BigDecimal.valueOf(0.79));
        vegetable.setProductName(name);

        vegetableRepository.save(vegetable);

        // When
        Optional<Vegetable> foundVegetable = vegetableRepository.findByInventoryId(savedInventory.getId());

        // Then
        assertThat(foundVegetable).isPresent();
        assertThat(foundVegetable.get().getInventory().getId()).isEqualTo(savedInventory.getId());
        assertThat(foundVegetable.get().getPricePer100g()).isEqualTo(BigDecimal.valueOf(0.79));
        assertThat(foundVegetable.get().getProductName()).isEqualTo(name);
    }

    @Test
    public void testFindByInventoryId_NotFound() {
        // When
        Optional<Vegetable> foundVegetable = vegetableRepository.findByInventoryId(999l);

        assertThat(foundVegetable).isNotPresent();
    }

}
