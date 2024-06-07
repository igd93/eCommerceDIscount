package com.example.grocery.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.QuantityDiscount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class QuantityDiscountRepositoryTest {

    private final BeerRepository beerRepository;
    private final InventoryRepository inventoryRepository;
    private final QuantityDiscountRepository quantityDiscountRepository;

    @Autowired
    public QuantityDiscountRepositoryTest(BeerRepository beerRepository, InventoryRepository inventoryRepository,
            QuantityDiscountRepository quantityDiscountRepository) {
        this.beerRepository = beerRepository;
        this.inventoryRepository = inventoryRepository;
        this.quantityDiscountRepository = quantityDiscountRepository;
    }

    private Beer savedBeer;
    private Inventory savedInventory;

    @BeforeEach
    void setUp() {
        Inventory inventory = new Inventory();
        inventory.setProductType("Beer");
        savedInventory = inventoryRepository.save(inventory);

        Beer beer = new Beer();
        beer.setInventory(savedInventory);
        beer.setProductName("Test Beer");
        beer.setIsPack(false);
        beer.setPricePerUnit(BigDecimal.valueOf(1.59));

        savedBeer = beerRepository.save(beer);

    }

    @Test
    public void findByBeerId() {

        // Given
        QuantityDiscount quantityDiscount = new QuantityDiscount();
        quantityDiscount.setBeer(savedBeer);
        quantityDiscount.setDiscountAmount(BigDecimal.valueOf(2.00));
        quantityDiscount.setInventory(savedInventory);
        quantityDiscount.setQuantity(6);

        quantityDiscountRepository.save(quantityDiscount);

        // When
        Optional<QuantityDiscount> foundDiscount = quantityDiscountRepository.findByBeerId(savedBeer.getId());

        // Then
        assertThat(foundDiscount).isPresent();
        assertThat(foundDiscount.get().getBeer().getId()).isEqualTo(savedBeer.getId());
        assertThat(foundDiscount.get().getDiscountAmount()).isEqualTo(BigDecimal.valueOf(2.00));
        assertThat(foundDiscount.get().getQuantity()).isEqualTo(6);
    }

    @Test
    public void findByBeerId_NotFound() {
        // When
        Optional<QuantityDiscount> foundDiscount = quantityDiscountRepository.findByBeerId(999L);

        // Then
        assertThat(foundDiscount).isNotPresent();
    }
}
