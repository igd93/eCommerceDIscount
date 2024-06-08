package com.example.grocery.controllers;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.example.grocery.entities.VegetableDiscount;
import com.example.grocery.services.VegetableDiscountService;

@ExtendWith(MockitoExtension.class)
public class VegetableDiscountControllerTest {

    @Mock
    private VegetableDiscountService vegetableDiscountService;

    @InjectMocks
    private VegetableDiscountController vegetableDiscountController;

    private VegetableDiscount vegetableDiscount1;
    private VegetableDiscount vegetableDiscount2;

    @BeforeEach
    void setUp() {
        vegetableDiscount1 = new VegetableDiscount();
        vegetableDiscount1.setMinGrams(100);
        vegetableDiscount1.setMaxGrams(300);
        vegetableDiscount1.setDiscountPercentage(BigDecimal.valueOf(0.1));

        vegetableDiscount2 = new VegetableDiscount();
        vegetableDiscount2.setMinGrams(301);
        vegetableDiscount2.setMaxGrams(500);
        vegetableDiscount2.setDiscountPercentage(BigDecimal.valueOf(0.2));
    }

    @Test
    void testGetAllVegetableDiscounts() {
        List<VegetableDiscount> discounts = Arrays.asList(vegetableDiscount1, vegetableDiscount2);
        when(vegetableDiscountService.findAll()).thenReturn(discounts);

        ResponseEntity<?> response = vegetableDiscountController.getAllVegetableDiscount();
        List<VegetableDiscount> body = (List<VegetableDiscount>) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(discounts);
    }

    @Test
    void testGetAllVegetableDiscountsEmpty() {
        when(vegetableDiscountService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = vegetableDiscountController.getAllVegetableDiscount();
        String body = (String) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(body).isEqualTo("No vegetable discounts available");
    }

    @Test
    void testGetVegetableDiscountById() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.of(vegetableDiscount1));

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.getVegetableDiscountById(1L);
        VegetableDiscount body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(vegetableDiscount1);
    }

    @Test
    void testGetVegetableDiscountByIdNotFound() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.getVegetableDiscountById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateVegetableDiscount() {
        when(vegetableDiscountService.save(vegetableDiscount1)).thenReturn(vegetableDiscount1);

        ResponseEntity<?> response = vegetableDiscountController.createVegetableDiscount(vegetableDiscount1);
        VegetableDiscount body = (VegetableDiscount) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(body).isEqualTo(vegetableDiscount1);
    }

    @Test
    void testCreateVegetableDiscountError() {
        when(vegetableDiscountService.save(vegetableDiscount1))
                .thenThrow(new RuntimeException("Error creating a vegetable discount"));

        ResponseEntity<?> response = vegetableDiscountController.createVegetableDiscount(vegetableDiscount1);
        String body = (String) response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(body).isEqualTo("Error creating a vegetable discount");
    }

    @Test
    void testUpdateVegetableDiscount() {
        VegetableDiscount updatedDiscount = new VegetableDiscount();
        updatedDiscount.setMinGrams(150);
        updatedDiscount.setMaxGrams(600);
        updatedDiscount.setDiscountPercentage(BigDecimal.valueOf(0.12));

        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.of(vegetableDiscount1));
        when(vegetableDiscountService.save(vegetableDiscount1)).thenReturn(updatedDiscount);

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.updateVegetableDiscount(1L,
                updatedDiscount);
        VegetableDiscount body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).isEqualTo(updatedDiscount);
    }

    @Test
    void testUpdateVegetableDiscountNotFound() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.updateVegetableDiscount(1L,
                new VegetableDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPartialUpdateVegetableDiscount() {
        VegetableDiscount partialUpdate = new VegetableDiscount();
        partialUpdate.setDiscountPercentage(BigDecimal.valueOf(0.2));

        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.of(vegetableDiscount1));
        when(vegetableDiscountService.save(vegetableDiscount1)).thenReturn(vegetableDiscount1);

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.partialUpdateVegetableDiscount(1L,
                partialUpdate);
        VegetableDiscount body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getDiscountPercentage()).isEqualTo(BigDecimal.valueOf(0.2));
    }

    @Test
    void testPartialUpdateVegetableDiscountNotFound() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<VegetableDiscount> response = vegetableDiscountController.partialUpdateVegetableDiscount(1L,
                new VegetableDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteDiscount() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.of(vegetableDiscount1));

        ResponseEntity<Void> response = vegetableDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(vegetableDiscountService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDiscountNotFound() {
        when(vegetableDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = vegetableDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(vegetableDiscountService, never()).deleteById(anyLong());
    }
}
