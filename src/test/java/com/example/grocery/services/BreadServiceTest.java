package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Bread;
import com.example.grocery.entities.Inventory;
import com.example.grocery.repositories.BreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BreadServiceTest {

    @Mock
    private BreadRepository breadRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private BreadServiceImpl breadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllBreads() {
        // Arrange
        Bread bread1 = new Bread();
        Bread bread2 = new Bread();
        List<Bread> breads = Arrays.asList(bread1, bread2);
        when(breadRepository.findAll()).thenReturn(breads);

        // Act
        List<Bread> result = breadService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(bread1, bread2);
    }

    @Test
    void findById_ShouldReturnBread() {
        // Arrange
        Bread bread = new Bread();
        when(breadRepository.findById(anyLong())).thenReturn(Optional.of(bread));

        // Act
        Optional<Bread> result = breadService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bread);
    }

    @Test
    void save_ShouldSaveBreadWithInventory() {
        // Arrange
        Bread bread = new Bread();
        Inventory inventory = new Inventory();
        when(inventoryService.save(any(Inventory.class))).thenReturn(inventory);
        when(breadRepository.save(any(Bread.class))).thenReturn(bread);

        // Act
        Bread result = breadService.save(bread);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getInventory()).isEqualTo(inventory);
        verify(inventoryService, times(1)).save(any(Inventory.class));
        verify(breadRepository, times(1)).save(any(Bread.class));
    }

    @Test
    void deleteById_ShouldDeleteBread() {
        // Arrange
        doNothing().when(breadRepository).deleteById(anyLong());

        // Act
        breadService.deleteById(1L);

        // Assert
        verify(breadRepository, times(1)).deleteById(1L);
    }
}
