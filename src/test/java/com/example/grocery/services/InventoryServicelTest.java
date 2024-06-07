package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.grocery.entities.Inventory;
import com.example.grocery.repositories.InventoryRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryServicelTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    void setUp() {
        inventory1 = new Inventory();
        inventory1.setId(1L);
        inventory1.setProductType("Bread");

        inventory2 = new Inventory();
        inventory2.setId(2L);
        inventory2.setProductType("Vegetable");

    }

    @Test
    void testFindAll() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        List<Inventory> inventories = inventoryService.findAll();

        assertThat(inventories).hasSize(2);
        assertThat(inventories).contains(inventory1, inventory2);
        verify(inventoryRepository).findAll();
    }

    @Test
    void testFindById() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory1));

        Optional<Inventory> foundInventory = inventoryService.findById(1L);

        assertThat(foundInventory).isPresent();
        assertThat(foundInventory.get()).isEqualTo(inventory1);
        verify(inventoryRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory1);

        Inventory savedInventory = inventoryService.save(inventory1);

        assertThat(savedInventory).isEqualTo(inventory1);
        verify(inventoryRepository).save(inventory1);
    }

    @Test
    void testDeleteById() {
        inventoryService.deleteById(1L);

        verify(inventoryRepository).deleteById(1L);
    }
}
