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

import com.example.grocery.entities.BreadDiscount;
import com.example.grocery.repositories.BreadDiscountRepository;

@ExtendWith(MockitoExtension.class)
public class BreadDiscountServicelTest {

    @Mock
    private BreadDiscountRepository breadDiscountRepository;

    @InjectMocks
    private BreadDiscountServiceImpl breadDiscountService;

    private BreadDiscount discount1;
    private BreadDiscount discount2;

    @BeforeEach
    void setUp() {
        discount1 = new BreadDiscount();
        discount1.setId(1L);
        discount1.setQuantityMultiplier(2);
        ;

        discount2 = new BreadDiscount();
        discount2.setId(2L);
        discount2.setQuantityMultiplier(3);
        ;
    }

    @Test
    void testFindAll() {
        when(breadDiscountRepository.findAll()).thenReturn(Arrays.asList(discount1, discount2));

        List<BreadDiscount> discounts = breadDiscountService.findAll();

        assertThat(discounts).hasSize(2);
        assertThat(discounts).contains(discount1, discount2);
        verify(breadDiscountRepository).findAll();
    }

    @Test
    void testFindById() {
        when(breadDiscountRepository.findById(1L)).thenReturn(Optional.of(discount1));

        Optional<BreadDiscount> foundDiscount = breadDiscountService.findById(1L);

        assertThat(foundDiscount).isPresent();
        assertThat(foundDiscount.get()).isEqualTo(discount1);
        verify(breadDiscountRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(breadDiscountRepository.save(any(BreadDiscount.class))).thenReturn(discount1);

        BreadDiscount savedDiscount = breadDiscountService.save(discount1);

        assertThat(savedDiscount).isEqualTo(discount1);
        verify(breadDiscountRepository).save(discount1);
    }

    @Test
    void testDeleteById() {
        breadDiscountService.deleteById(1L);

        verify(breadDiscountRepository).deleteById(1L);
    }
}
