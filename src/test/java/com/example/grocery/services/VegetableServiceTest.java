package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.Vegetable;
import com.example.grocery.repositories.InventoryRepository;
import com.example.grocery.repositories.VegetableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class VegetableServiceTest {

    @Mock
    private VegetableRepository vegetableRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private VegetableServiceImpl vegetableService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllVegetables() {
        // Arrange
        Vegetable vegetable1 = new Vegetable();
        Vegetable vegetable2 = new Vegetable();
        List<Vegetable> vegetables = Arrays.asList(vegetable1, vegetable2);
        when(vegetableRepository.findAll()).thenReturn(vegetables);

        // Act
        List<Vegetable> result = vegetableService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(vegetable1, vegetable2);
    }

    @Test
    void findById_ShouldReturnVegetable() {
        // Arrange
        Vegetable vegetable = new Vegetable();
        when(vegetableRepository.findById(anyLong())).thenReturn(Optional.of(vegetable));

        // Act
        Optional<Vegetable> result = vegetableService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(vegetable);
    }

    @Test
    void save_ShouldSaveVegetableWithInventory() {
        // Arrange
        Vegetable vegetable = new Vegetable();
        Inventory inventory = new Inventory();
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        when(vegetableRepository.save(any(Vegetable.class))).thenReturn(vegetable);

        // Act
        Vegetable result = vegetableService.save(vegetable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getInventory()).isEqualTo(inventory);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(vegetableRepository, times(1)).save(any(Vegetable.class));
    }

    @Test
    void deleteById_ShouldDeleteVegetable() {
        // Arrange
        doNothing().when(vegetableRepository).deleteById(anyLong());

        // Act
        vegetableService.deleteById(1L);

        // Assert
        verify(vegetableRepository, times(1)).deleteById(1L);
    }
}
