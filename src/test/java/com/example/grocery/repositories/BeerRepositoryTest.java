package com.example.grocery.repositories;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BeerRepositoryTest {

    private final BeerRepository beerRepository;
    private final InventoryRepository inventoryRepository;

    private Inventory savedInventory;

    @Autowired
    public BeerRepositoryTest(BeerRepository beerRepository, InventoryRepository inventoryRepository) {
        this.beerRepository = beerRepository;
        this.inventoryRepository = inventoryRepository;
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
        Beer beer = new Beer();
        String name = "Test Beer";
        beer.setInventory(savedInventory);
        beer.setProductName(name);
        beer.setIsPack(false);
        beer.setPricePerUnit(BigDecimal.valueOf(1.59));

        beerRepository.save(beer);

        // When
        Optional<Beer> foundBeer = beerRepository.findByInventoryId(savedInventory.getId());

        // Then
        assertThat(foundBeer).isPresent();
        assertThat(foundBeer.get().getInventory().getId()).isEqualTo(savedInventory.getId());
        assertThat(foundBeer.get().getProductName()).isEqualTo(name);
        assertThat(foundBeer.get().getIsPack()).isEqualTo(false);
        assertThat(foundBeer.get().getPricePerUnit()).isEqualTo(BigDecimal.valueOf(1.59));
    }

    @Test
    public void testFindByInventoryId_NotFound() {
        // When
        Optional<Beer> foundBeer = beerRepository.findByInventoryId(999L);

        // Then
        assertThat(foundBeer).isNotPresent();
    }
}
