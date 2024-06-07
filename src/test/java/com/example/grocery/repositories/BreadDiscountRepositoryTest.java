package com.example.grocery.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.grocery.entities.BreadDiscount;

@DataJpaTest
public class BreadDiscountRepositoryTest {

    private final BreadDiscountRepository breadDiscountRepository;

    @Autowired
    public BreadDiscountRepositoryTest(BreadDiscountRepository breadDiscountRepository) {
        this.breadDiscountRepository = breadDiscountRepository;
    }

    @BeforeEach
    void setUp() {
        breadDiscountRepository.deleteAll();
        BreadDiscount breadDiscount = new BreadDiscount();
        breadDiscount.setMinDaysOld(3);
        breadDiscount.setMaxDaysOld(5);
        breadDiscount.setQuantityMultiplier(2);

        breadDiscountRepository.save(breadDiscount);
    }

    @Test
    public void testFindDiscountByIdWithinRange() {
        BreadDiscount discount = breadDiscountRepository.findDiscountByDaysOld(4);
        assertThat(discount).isNotNull();
        assertThat(discount.getMinDaysOld()).isLessThanOrEqualTo(4);
        assertThat(discount.getMaxDaysOld()).isGreaterThanOrEqualTo(4);
        assertThat(discount.getQuantityMultiplier()).isEqualTo(2);

    }

    @Test
    public void testFindDiscountByIdOutsideRange() {
        BreadDiscount discount = breadDiscountRepository.findDiscountByDaysOld(11);
        assertThat(discount).isNull();
    }

}
