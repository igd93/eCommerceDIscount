package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.grocery.entities.Inventory;
import com.example.grocery.services.InventoryService;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    void setUp() {
        inventory1 = new Inventory();
        inventory1.setProductType("Type1");

        inventory2 = new Inventory();
        inventory2.setProductType("Type2");
    }

    @Test
    void testGetAllInventory() {
        List<Inventory> inventoryList = Arrays.asList(inventory1, inventory2);
        when(inventoryService.findAll()).thenReturn(inventoryList);

        ResponseEntity<?> response = inventoryController.getAllInventory();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(inventoryList);
    }

    @Test
    void testGetInventoryById() {
        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory1));

        ResponseEntity<Inventory> response = inventoryController.getInventoryById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(inventory1);
    }

    @Test
    void testGetInventoryByIdNotFound() {
        when(inventoryService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Inventory> response = inventoryController.getInventoryById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateInventoryItem() {
        when(inventoryService.save(inventory1)).thenReturn(inventory1);

        ResponseEntity<?> response = inventoryController.createInventoryItem(inventory1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(inventory1);
    }

    @Test
    void testUpdateInventory() {
        Inventory updatedInventory = new Inventory();
        updatedInventory.setProductType("updated");

        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory1));
        when(inventoryService.save(inventory1)).thenReturn(updatedInventory);

        ResponseEntity<Inventory> response = inventoryController.updateInventory(1L, updatedInventory);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getProductType()).isEqualTo("updated");
    }

    @Test
    void testUpdateInventoryNotFound() {
        when(inventoryService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Inventory> response = inventoryController.updateInventory(1L, new Inventory());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPartialUpdateInventory() {
        Inventory partialUpdate = new Inventory();
        partialUpdate.setProductType("updated");

        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory1));
        when(inventoryService.save(inventory1)).thenReturn(inventory1);

        ResponseEntity<Inventory> response = inventoryController.partialUpdateInventory(1L, partialUpdate);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getProductType()).isEqualTo("updated");
    }

    @Test
    void testPartialUpdateInventoryNotFound() {
        when(inventoryService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Inventory> response = inventoryController.partialUpdateInventory(1L, new Inventory());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteInventoryItem() {
        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory1));

        ResponseEntity<Void> response = inventoryController.deleteInventoryItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(inventoryService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInventoryItemNotFound() {
        when(inventoryService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = inventoryController.deleteInventoryItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(inventoryService, never()).deleteById(anyLong());
    }
}
