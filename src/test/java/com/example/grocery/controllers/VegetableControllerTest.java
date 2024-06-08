package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.Vegetable;
import com.example.grocery.services.InventoryService;
import com.example.grocery.services.VegetableService;

@ExtendWith(MockitoExtension.class)
public class VegetableControllerTest {

    @Mock
    private VegetableService vegetableService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private VegetableController vegetableController;

    private Vegetable vegetable1;
    private Vegetable vegetable2;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    void setUp() {
        inventory1 = new Inventory();
        inventory2 = new Inventory();

        inventory1.setId(1L);
        inventory1.setProductType("Vegetable");
        inventory2.setId(2L);
        inventory2.setProductType("Vegetable");

        vegetable1 = new Vegetable();
        vegetable2 = new Vegetable();

        vegetable1.setId(1L);
        vegetable1.setInventory(inventory1);
        vegetable1.setPricePer100g(BigDecimal.valueOf(1.00));
        vegetable1.setProductName("Carrot");

        vegetable2.setId(2L);
        vegetable2.setInventory(inventory2);
        vegetable2.setPricePer100g(BigDecimal.valueOf(1.50));
        vegetable2.setProductName("Tomato");

    }

    @Test
    void testGetAllVegetables() {
        List<Vegetable> vegetables = Arrays.asList(vegetable1, vegetable2);
        when(vegetableService.findAll()).thenReturn(vegetables);

        List<Vegetable> result = vegetableController.getAllVegetables();

        assertThat(result).hasSize(2).contains(vegetable1, vegetable2);
        verify(vegetableService, times(1)).findAll();
    }

    @Test
    void testGetVegetableById() {
        when(vegetableService.findById(1L)).thenReturn(Optional.of(vegetable1));

        ResponseEntity<Vegetable> response = vegetableController.getVegetableById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(vegetable1);
        verify(vegetableService, times(1)).findById(1L);
    }

    @Test
    void testGetVegetableByIdNotFound() {
        when(vegetableService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Vegetable> response = vegetableController.getVegetableById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(vegetableService, times(1)).findById(1L);
    }

    @Test
    void testCreateVegetable() {
        when(vegetableService.save(any(Vegetable.class))).thenReturn(vegetable1);

        ResponseEntity<?> response = vegetableController.createVegetable(vegetable1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(vegetable1);
        verify(vegetableService, times(1)).save(vegetable1);
    }

    @Test
    void testUpdateVegetable() {
        when(vegetableService.findById(1L)).thenReturn(Optional.of(vegetable1));
        when(vegetableService.save(any(Vegetable.class))).thenReturn(vegetable1);

        ResponseEntity<Vegetable> response = vegetableController.updateVegetable(1L, vegetable2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(vegetableService, times(1)).findById(1L);
        verify(vegetableService, times(1)).save(any(Vegetable.class));
    }

    @Test
    void testUpdateVegetableNotFound() {
        when(vegetableService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Vegetable> response = vegetableController.updateVegetable(1L, vegetable2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(vegetableService, times(1)).findById(1L);
    }

    @Test
    void testPartialUpdateVegetable() {
        when(vegetableService.findById(1L)).thenReturn(Optional.of(vegetable1));
        when(vegetableService.save(any(Vegetable.class))).thenReturn(vegetable1);

        ResponseEntity<Vegetable> response = vegetableController.partialUpdateVegetable(1L, vegetable2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(vegetableService, times(1)).findById(1L);
        verify(vegetableService, times(1)).save(any(Vegetable.class));
    }

    @Test
    void testPartialUpdateVegetableNotFound() {
        when(vegetableService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Vegetable> response = vegetableController.partialUpdateVegetable(1L, vegetable2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(vegetableService, times(1)).findById(1L);
    }

    @Test
    void testDeleteVegetable() {
        when(vegetableService.findById(1L)).thenReturn(Optional.of(vegetable1));
        doNothing().when(vegetableService).deleteById(1L);

        ResponseEntity<Void> response = vegetableController.deleteVegetable(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(vegetableService, times(1)).findById(1L);
        verify(vegetableService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVegetableNotFound() {
        when(vegetableService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = vegetableController.deleteVegetable(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(vegetableService, times(1)).findById(1L);
    }

}
