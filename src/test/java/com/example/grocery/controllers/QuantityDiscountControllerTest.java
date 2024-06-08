package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.grocery.entities.Beer;
import com.example.grocery.entities.Inventory;
import com.example.grocery.entities.QuantityDiscount;
import com.example.grocery.services.QuantityDiscountService;
import com.example.grocery.controllers.*;

@ExtendWith(MockitoExtension.class)
public class QuantityDiscountControllerTest {

    @Mock
    private QuantityDiscountService quantityDiscountService;

    @InjectMocks
    private QuantityDiscountController quantityDiscountController;

    private QuantityDiscount quantityDiscount1;
    private QuantityDiscount quantityDiscount2;
    private Inventory inventory1;
    private Inventory inventory2;
    private Beer beer1;
    private Beer beer2;

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

        quantityDiscount1 = new QuantityDiscount();
        quantityDiscount1.setId(1L);
        quantityDiscount1.setBeer(beer1);
        quantityDiscount1.setInventory(inventory1);
        quantityDiscount1.setDiscountAmount(BigDecimal.valueOf(2.00));
        quantityDiscount1.setQuantity(3);

        quantityDiscount2 = new QuantityDiscount();
        quantityDiscount2.setId(2L);
        quantityDiscount2.setBeer(beer2);
        quantityDiscount2.setInventory(inventory2);
        quantityDiscount2.setDiscountAmount(BigDecimal.valueOf(3.00));
        quantityDiscount2.setQuantity(6);
    }

    @Test
    void testGetAllQuantityDiscounts() {
        List<QuantityDiscount> discounts = Arrays.asList(quantityDiscount1, quantityDiscount2);
        when(quantityDiscountService.findAll()).thenReturn(discounts);

        ResponseEntity<?> response = quantityDiscountController.getAllQuantityDiscounts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(discounts);
    }

    @Test
    void testGetAllQuantityDiscountsEmpty() {
        when(quantityDiscountService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = quantityDiscountController.getAllQuantityDiscounts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("There are no bread discounts");
    }

    @Test
    void testGetQuantityDiscountById() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.of(quantityDiscount1));

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.getQuantityDiscountById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(quantityDiscount1);
    }

    @Test
    void testGetQuantityDiscountByIdNotFound() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.getQuantityDiscountById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateQuantityDiscount() {
        when(quantityDiscountService.save(quantityDiscount1)).thenReturn(quantityDiscount1);

        ResponseEntity<?> response = quantityDiscountController.createQuantityDiscount(quantityDiscount1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(quantityDiscount1);
    }

    @Test
    void testCreateQuantityDiscountError() {
        when(quantityDiscountService.save(quantityDiscount1))
                .thenThrow(new RuntimeException("Error creating a quantity discount"));

        ResponseEntity<?> response = quantityDiscountController.createQuantityDiscount(quantityDiscount1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Error creating a quantityDiscount");
    }

    @Test
    void testUpdateQuantityDiscount() {
        QuantityDiscount updatedDiscount = new QuantityDiscount();

        updatedDiscount.setId(3L);
        updatedDiscount.setBeer(beer2);
        updatedDiscount.setInventory(inventory2);
        updatedDiscount.setDiscountAmount(BigDecimal.valueOf(4.00));
        updatedDiscount.setQuantity(12);

        when(quantityDiscountService.findById(1L)).thenReturn(Optional.of(quantityDiscount1));
        when(quantityDiscountService.save(quantityDiscount1)).thenReturn(updatedDiscount);

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.updateQuantityDiscount(1L,
                updatedDiscount);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedDiscount);
    }

    @Test
    void testUpdateQuantityDiscountNotFound() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.updateQuantityDiscount(1L,
                new QuantityDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPartialUpdateQuantityDiscount() {
        QuantityDiscount partialUpdate = new QuantityDiscount();
        partialUpdate.setDiscountAmount(BigDecimal.valueOf(10.00));

        when(quantityDiscountService.findById(1L)).thenReturn(Optional.of(quantityDiscount1));
        when(quantityDiscountService.save(quantityDiscount1)).thenReturn(quantityDiscount1);

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.partialUpdateQuantityDiscount(1L,
                partialUpdate);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getDiscountAmount()).isEqualTo(BigDecimal.valueOf(10.00));
    }

    @Test
    void testPartialUpdateQuantityDiscountNotFound() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<QuantityDiscount> response = quantityDiscountController.partialUpdateQuantityDiscount(1L,
                new QuantityDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteDiscount() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.of(quantityDiscount1));

        ResponseEntity<Void> response = quantityDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(quantityDiscountService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDiscountNotFound() {
        when(quantityDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = quantityDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(quantityDiscountService, never()).deleteById(anyLong());
    }
}