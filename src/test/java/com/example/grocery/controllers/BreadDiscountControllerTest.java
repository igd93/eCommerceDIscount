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

import com.example.grocery.entities.BreadDiscount;
import com.example.grocery.services.BreadDiscountService;

@ExtendWith(MockitoExtension.class)
class BreadDiscountControllerTest {

    @Mock
    private BreadDiscountService breadDiscountService;

    @InjectMocks
    private BreadDiscountController breadDiscountController;

    private BreadDiscount breadDiscount1;
    private BreadDiscount breadDiscount2;

    @BeforeEach
    void setUp() {
        breadDiscount1 = new BreadDiscount();
        breadDiscount1.setMinDaysOld(2);
        breadDiscount1.setMaxDaysOld(5);
        breadDiscount1.setQuantityMultiplier(3);

        breadDiscount2 = new BreadDiscount();
        breadDiscount2.setMinDaysOld(1);
        breadDiscount2.setMaxDaysOld(3);
        breadDiscount2.setQuantityMultiplier(2);
    }

    @Test
    void testGetAllBreadDiscounts() {
        List<BreadDiscount> discounts = Arrays.asList(breadDiscount1, breadDiscount2);
        when(breadDiscountService.findAll()).thenReturn(discounts);

        ResponseEntity<?> response = breadDiscountController.getAllBreadDiscounts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(discounts);
    }

    @Test
    void testGetAllBreadDiscountsEmpty() {
        when(breadDiscountService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<?> response = breadDiscountController.getAllBreadDiscounts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("No discounts available for bread at the moment");
    }

    @Test
    void testGetBreadDiscountById() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.of(breadDiscount1));

        ResponseEntity<BreadDiscount> response = breadDiscountController.getBreadDiscountById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(breadDiscount1);
    }

    @Test
    void testGetBreadDiscountByIdNotFound() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BreadDiscount> response = breadDiscountController.getBreadDiscountById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateBreadDiscount() {
        when(breadDiscountService.save(breadDiscount1)).thenReturn(breadDiscount1);

        ResponseEntity<?> response = breadDiscountController.createBreadDiscount(breadDiscount1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(breadDiscount1);
    }

    @Test
    void testCreateBreadDiscountError() {
        when(breadDiscountService.save(breadDiscount1))
                .thenThrow(new RuntimeException("Error creating a Bread Discount"));

        ResponseEntity<?> response = breadDiscountController.createBreadDiscount(breadDiscount1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("Error creating a Bread Discount");
    }

    @Test
    void testUpdateBreadDiscount() {
        BreadDiscount updatedBreadDiscount = new BreadDiscount();
        updatedBreadDiscount.setMinDaysOld(3);
        updatedBreadDiscount.setMaxDaysOld(6);
        updatedBreadDiscount.setQuantityMultiplier(5);

        when(breadDiscountService.findById(1L)).thenReturn(Optional.of(breadDiscount1));
        when(breadDiscountService.save(breadDiscount1)).thenReturn(updatedBreadDiscount);

        ResponseEntity<BreadDiscount> response = breadDiscountController.updateBreadDiscount(1L, updatedBreadDiscount);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedBreadDiscount);
    }

    @Test
    void testUpdateBreadDiscountNotFound() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BreadDiscount> response = breadDiscountController.updateBreadDiscount(1L, new BreadDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPartialUpdateBreadDiscount() {
        BreadDiscount partialUpdate = new BreadDiscount();
        partialUpdate.setMinDaysOld(4);

        when(breadDiscountService.findById(1L)).thenReturn(Optional.of(breadDiscount1));
        when(breadDiscountService.save(breadDiscount1)).thenReturn(breadDiscount1);

        ResponseEntity<BreadDiscount> response = breadDiscountController.partialUpdateBreadDiscount(1L, partialUpdate);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(breadDiscount1.getMinDaysOld()).isEqualTo(4);
    }

    @Test
    void testPartialUpdateBreadDiscountNotFound() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BreadDiscount> response = breadDiscountController.partialUpdateBreadDiscount(1L,
                new BreadDiscount());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteDiscount() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.of(breadDiscount1));

        ResponseEntity<Void> response = breadDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(breadDiscountService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDiscountNotFound() {
        when(breadDiscountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = breadDiscountController.deleteDiscount(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(breadDiscountService, never()).deleteById(anyLong());
    }
}
