package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;
import com.example.grocery.repositories.BeerRepository;
import com.example.grocery.repositories.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private BeerServiceImpl beerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllBeers() {
        // Arrange
        Beer beer1 = new Beer();
        Beer beer2 = new Beer();
        List<Beer> beers = Arrays.asList(beer1, beer2);
        when(beerRepository.findAll()).thenReturn(beers);

        // Act
        List<Beer> result = beerService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(beer1, beer2);
    }

    @Test
    void findById_ShouldReturnBeer() {
        // Arrange
        Beer beer = new Beer();
        when(beerRepository.findById(anyLong())).thenReturn(Optional.of(beer));

        // Act
        Optional<Beer> result = beerService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(beer);
    }

    @Test
    void save_ShouldSaveBeerWithInventory() {
        // Arrange
        Beer beer = new Beer();
        Inventory inventory = new Inventory();
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(beerRepository.save(any(Beer.class))).thenReturn(beer);

        // Act
        Beer result = beerService.save(beer);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getInventory()).isEqualTo(inventory);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void deleteById_ShouldDeleteBeer() {
        // Arrange
        doNothing().when(beerRepository).deleteById(anyLong());

        // Act
        beerService.deleteById(1L);

        // Assert
        verify(beerRepository, times(1)).deleteById(1L);
    }
}
