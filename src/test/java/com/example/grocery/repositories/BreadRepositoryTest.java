package com.example.grocery.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.Bread;
import com.example.grocery.entities.Inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class BreadRepositoryTest {

    private final BreadRepository breadRepository;
    private final InventoryRepository inventoryRepository;

    private Inventory savedInventory;

    @Autowired
    public BreadRepositoryTest(BreadRepository breadRepository, InventoryRepository inventoryRepository) {
        this.breadRepository = breadRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @BeforeEach
    void setUp() {
        Inventory inventory = new Inventory();
        inventory.setProductType("Bread");
        savedInventory = inventoryRepository.save(inventory);
    }

    @Test
    public void testFindByInventoryId() {
        // Given
        Bread bread = new Bread();
        String name = "Test Bread";
        bread.setAge(1);
        bread.setInventory(savedInventory);
        bread.setPricePerUnit(BigDecimal.valueOf(0.79));
        bread.setProductName(name);

        breadRepository.save(bread);

        // When
        Optional<Bread> foundBread = breadRepository.findByInventoryId(savedInventory.getId());

        // Then
        assertThat(foundBread).isPresent();
        assertThat(foundBread.get().getAge()).isEqualTo(1);
        assertThat(foundBread.get().getInventory().getId()).isEqualTo(savedInventory.getId());
        assertThat(foundBread.get().getPricePerUnit()).isEqualTo(BigDecimal.valueOf(0.79));
        assertThat(foundBread.get().getProductName()).isEqualTo(name);

    }

    @Test
    public void testFindByInventoryId_NotFound() {

        // When
        Optional<Bread> foundBread = breadRepository.findByInventoryId(999L);

        // Then
        assertThat(foundBread).isNotPresent();
    }

}
