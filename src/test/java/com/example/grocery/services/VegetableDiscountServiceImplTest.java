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

import com.example.grocery.entities.VegetableDiscount;
import com.example.grocery.repositories.VegetableDiscountRepository;

@ExtendWith(MockitoExtension.class)
public class VegetableDiscountServiceImplTest {

    @Mock
    private VegetableDiscountRepository vegetableDiscountRepository;

    @InjectMocks
    private VegetableDiscountServiceImpl vegetableDiscountService;

    private VegetableDiscount discount1;
    private VegetableDiscount discount2;

    @BeforeEach
    void setUp() {
        discount1 = new VegetableDiscount();
        discount1.setId(1L);
        discount1.setDiscountPercentage(BigDecimal.valueOf(0.1));

        discount2 = new VegetableDiscount();
        discount2.setId(2L);
        discount2.setDiscountPercentage(BigDecimal.valueOf(0.15));
    }

    @Test
    void testFindAll() {
        when(vegetableDiscountRepository.findAll()).thenReturn(Arrays.asList(discount1, discount2));

        List<VegetableDiscount> discounts = vegetableDiscountService.findAll();

        assertThat(discounts).hasSize(2);
        assertThat(discounts).contains(discount1, discount2);
        verify(vegetableDiscountRepository).findAll();
    }

    @Test
    void testFindById() {
        when(vegetableDiscountRepository.findById(1L)).thenReturn(Optional.of(discount1));

        Optional<VegetableDiscount> foundDiscount = vegetableDiscountService.findById(1L);

        assertThat(foundDiscount).isPresent();
        assertThat(foundDiscount.get()).isEqualTo(discount1);
        verify(vegetableDiscountRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(vegetableDiscountRepository.save(any(VegetableDiscount.class))).thenReturn(discount1);

        VegetableDiscount savedDiscount = vegetableDiscountService.save(discount1);

        assertThat(savedDiscount).isEqualTo(discount1);
        verify(vegetableDiscountRepository).save(discount1);
    }

    @Test
    void testDeleteById() {
        vegetableDiscountService.deleteById(1L);

        verify(vegetableDiscountRepository).deleteById(1L);
    }
}
