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

import com.example.grocery.entities.Bread;
import com.example.grocery.entities.Inventory;
import com.example.grocery.services.BreadService;
import com.example.grocery.services.InventoryService;

@ExtendWith(MockitoExtension.class)
public class BreadControllerTest {

    @Mock
    private BreadService breadService;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private BreadController breadController;

    private Bread bread1;
    private Bread bread2;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    void setUp() {
        inventory1 = new Inventory();
        inventory2 = new Inventory();

        inventory1.setId(1L);
        inventory1.setProductType("Bread");
        inventory2.setId(2L);
        inventory2.setProductType("Bread");

        bread1 = new Bread();
        bread2 = new Bread();

        bread1.setId(1L);
        bread1.setInventory(inventory1);
        bread1.setAge(1);
        bread1.setPricePerUnit(BigDecimal.valueOf(1.50));
        bread1.setProductName("Ciabatta");

        bread2.setId(2L);
        bread2.setInventory(inventory2);
        bread2.setAge(2);
        bread2.setPricePerUnit(BigDecimal.valueOf(1.20));
        bread2.setProductName("Baguette");

    }

    @Test
    void testGetAllVegetables() {
        List<Bread> breads = Arrays.asList(bread1, bread2);
        when(breadService.findAll()).thenReturn(breads);

        List<Bread> result = breadController.getAllBreads();

        assertThat(result).hasSize(2).contains(bread1, bread2);
        verify(breadService, times(1)).findAll();
    }

    @Test
    void testGetBreadById() {
        when(breadService.findById(1L)).thenReturn(Optional.of(bread1));

        ResponseEntity<Bread> response = breadController.getBreadById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(bread1);
        verify(breadService, times(1)).findById(1L);
    }

    @Test
    void testGetBreadByIdNotFound() {
        when(breadService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Bread> response = breadController.getBreadById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(breadService, times(1)).findById(1L);
    }

    @Test
    void testCreateBread() {
        when(breadService.save(any(Bread.class))).thenReturn(bread1);

        ResponseEntity<?> response = breadController.createBread(bread1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(bread1);
        verify(breadService, times(1)).save(bread1);
    }

    @Test
    void testUpdateBread() {
        when(breadService.findById(1L)).thenReturn(Optional.of(bread1));
        when(breadService.save(any(Bread.class))).thenReturn(bread1);

        ResponseEntity<Bread> response = breadController.updateBread(1L, bread2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(breadService, times(1)).findById(1L);
        verify(breadService, times(1)).save(any(Bread.class));
    }

    @Test
    void testUpdateBreadNotFound() {
        when(breadService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Bread> response = breadController.updateBread(1L, bread2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(breadService, times(1)).findById(1L);
    }

    @Test
    void testPartialUpdateBread() {
        when(breadService.findById(1L)).thenReturn(Optional.of(bread1));
        when(breadService.save(any(Bread.class))).thenReturn(bread1);

        ResponseEntity<Bread> response = breadController.partialUpdateBread(1L, bread2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(breadService, times(1)).findById(1L);
        verify(breadService, times(1)).save(any(Bread.class));
    }

    @Test
    void testPartialUpdateBreadNotFound() {
        when(breadService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Bread> response = breadController.partialUpdateBread(1L, bread2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(breadService, times(1)).findById(1L);
    }

    @Test
    void testDeleteBread() {
        when(breadService.findById(1L)).thenReturn(Optional.of(bread1));
        doNothing().when(breadService).deleteById(1L);

        ResponseEntity<Void> response = breadController.deleteBread(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(breadService, times(1)).findById(1L);
        verify(breadService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBreadNotFound() {
        when(breadService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = breadController.deleteBread(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(breadService, times(1)).findById(1L);
    }

}
