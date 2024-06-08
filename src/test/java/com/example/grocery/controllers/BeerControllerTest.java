package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;
import com.example.grocery.services.BeerService;
import com.example.grocery.services.InventoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    @Mock
    private BeerService beerService;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private BeerController beerController;

    private Beer beer1;
    private Beer beer2;

    private Inventory inventory1;
    private Inventory inventory2;

    @BeforeEach
    void setUp() {
        inventory1 = new Inventory();
        inventory2 = new Inventory();

        inventory1.setId(1L);
        inventory1.setProductType("Beer");
        inventory2.setId(2L);
        inventory2.setProductType("Beer");

        beer1 = new Beer();
        beer2 = new Beer();

        beer1.setId(1L);
        beer1.setInventory(inventory1);
        beer1.setIsPack(false);
        beer1.setPricePerUnit(BigDecimal.valueOf(1.00));
        beer1.setProductName("Dutch");

        beer1.setId(2L);
        beer2.setInventory(inventory2);
        beer2.setIsPack(false);
        beer2.setPricePerUnit(BigDecimal.valueOf(2.00));
        beer2.setProductName("German");

    }

    @Test
    void testGetAllBeers() {
        List<Beer> beers = Arrays.asList(beer1, beer2);
        when(beerService.findAll()).thenReturn(beers);

        List<Beer> result = beerController.getAllBeers();

        assertThat(result).hasSize(2).contains(beer1, beer2);
        verify(beerService, times(1)).findAll();
    }

    @Test
    void testGetBeerById() {
        when(beerService.findById(1L)).thenReturn(Optional.of(beer1));

        ResponseEntity<Beer> response = beerController.getBeerById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(beer1);
        verify(beerService, times(1)).findById(1L);
    }

    @Test
    void testGetBeerByIdNotFound() {
        when(beerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Beer> response = beerController.getBeerById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(beerService, times(1)).findById(1L);
    }

    @Test
    void testCreateBeer() {
        when(beerService.save(any(Beer.class))).thenReturn(beer1);

        ResponseEntity<?> response = beerController.createBeer(beer1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(beer1);
        verify(beerService, times(1)).save(beer1);
    }

    @Test
    void testUpdateBeer() {
        when(beerService.findById(1L)).thenReturn(Optional.of(beer1));
        when(beerService.save(any(Beer.class))).thenReturn(beer1);

        ResponseEntity<Beer> response = beerController.updateBeer(1L, beer2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(beerService, times(1)).findById(1L);
        verify(beerService, times(1)).save(any(Beer.class));
    }

    @Test
    void testUpdateBeerNotFound() {
        when(beerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Beer> response = beerController.updateBeer(1L, beer2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(beerService, times(1)).findById(1L);
    }

    @Test
    void testPartialUpdateBeer() {
        when(beerService.findById(1L)).thenReturn(Optional.of(beer1));
        when(beerService.save(any(Beer.class))).thenReturn(beer1);

        ResponseEntity<Beer> response = beerController.partialUpdateBeer(1L, beer2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(beerService, times(1)).findById(1L);
        verify(beerService, times(1)).save(any(Beer.class));
        assertThat(beer1.getProductName()).isEqualTo("German");
    }

    @Test
    void testPartialUpdateBeerNotFound() {
        when(beerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Beer> response = beerController.partialUpdateBeer(1L, beer2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(beerService, times(1)).findById(1L);
    }

    @Test
    void testDeleteBeer() {
        when(beerService.findById(1L)).thenReturn(Optional.of(beer1));
        doNothing().when(beerService).deleteById(1L);

        ResponseEntity<Void> response = beerController.deleteBeer(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(beerService, times(1)).findById(1L);
        verify(beerService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBeerNotFound() {
        when(beerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = beerController.deleteBeer(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(beerService, times(1)).findById(1L);
    }
}
