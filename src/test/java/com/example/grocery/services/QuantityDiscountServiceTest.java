package com.example.grocery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.grocery.entities.QuantityDiscount;
import com.example.grocery.repositories.QuantityDiscountRepository;

@ExtendWith(MockitoExtension.class)
public class QuantityDiscountServiceTest {

    @Mock
    private QuantityDiscountRepository quantityDiscountRepository;

    @InjectMocks
    private QuantityDiscountServiceImpl quantityDiscountService;

    private QuantityDiscount discount1;
    private QuantityDiscount discount2;

    @BeforeEach
    void setUp() {
        discount1 = new QuantityDiscount();
        discount1.setId(1L);
        discount1.setDiscountAmount(BigDecimal.valueOf(2.00));

        discount2 = new QuantityDiscount();
        discount2.setId(2L);
        discount2.setDiscountAmount(BigDecimal.valueOf(2.00));
    }

    @Test
    void testFindAll() {
        when(quantityDiscountRepository.findAll()).thenReturn(Arrays.asList(discount1, discount2));

        List<QuantityDiscount> discounts = quantityDiscountService.findAll();

        assertThat(discounts).hasSize(2);
        assertThat(discounts).contains(discount1, discount2);
        verify(quantityDiscountRepository).findAll();
    }

    @Test
    void testFindById() {
        when(quantityDiscountRepository.findById(1L)).thenReturn(Optional.of(discount1));

        Optional<QuantityDiscount> foundDiscount = quantityDiscountService.findById(1L);

        assertThat(foundDiscount).isPresent();
        assertThat(foundDiscount.get()).isEqualTo(discount1);
        verify(quantityDiscountRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(quantityDiscountRepository.save(any(QuantityDiscount.class))).thenReturn(discount1);

        QuantityDiscount savedDiscount = quantityDiscountService.save(discount1);

        assertThat(savedDiscount).isEqualTo(discount1);
        verify(quantityDiscountRepository).save(discount1);
    }

    @Test
    void testDeleteById() {
        quantityDiscountService.deleteById(1L);

        verify(quantityDiscountRepository).deleteById(1L);
    }
}
