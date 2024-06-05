package com.example.grocery.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class BreadDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Integer minDaysOld;

    @Column(nullable=false)
    private Integer maxDaysOld;
    
    @Column(nullable=false)
    private Integer quantityMultiplier;
}
