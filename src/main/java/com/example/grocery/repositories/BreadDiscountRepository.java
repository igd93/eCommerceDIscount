package com.example.grocery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.grocery.entities.BreadDiscount;

public interface BreadDiscountRepository extends JpaRepository<BreadDiscount, Long>{
    @Query("SELECT bd FROM BreadDiscount bd WHERE bd.minDaysOld <= :age AND bd.maxDaysOld >= :age")
    BreadDiscount findDiscountByDaysOld(@Param("age") int age);
    
}
