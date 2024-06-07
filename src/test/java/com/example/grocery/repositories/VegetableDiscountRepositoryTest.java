package com.example.grocery.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.VegetableDiscount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@DataJpaTest
public class VegetableDiscountRepositoryTest {

    private final VegetableDiscountRepository vegetableDiscountRepository;

    @Autowired
    public VegetableDiscountRepositoryTest(VegetableDiscountRepository vegetableDiscountRepository) {
        this.vegetableDiscountRepository = vegetableDiscountRepository;
    }

    @BeforeEach
    void setUp() {
        vegetableDiscountRepository.deleteAll();
        VegetableDiscount vegetableDiscount = new VegetableDiscount();
        vegetableDiscount.setDiscountPercentage(BigDecimal.valueOf(0.07));
        vegetableDiscount.setMinGrams(100);
        vegetableDiscount.setMaxGrams(200);
        vegetableDiscountRepository.save(vegetableDiscount);
    }

    @Test
    public void testFindDiscountByGramsWithinRange() {
        VegetableDiscount discount = vegetableDiscountRepository.findDiscountByGrams(150);
        assertThat(discount).isNotNull();
        assertThat(discount.getMinGrams()).isLessThanOrEqualTo(150);
        assertThat(discount.getMaxGrams()).isGreaterThanOrEqualTo(150);
        assertThat(discount.getDiscountPercentage()).isEqualTo(BigDecimal.valueOf(0.07));
    }

    @Test
    public void testFindDiscountByGramsOutsideRange() {
        VegetableDiscount discount = vegetableDiscountRepository.findDiscountByGrams(350);
        assertThat(discount).isNull();
    }

}
