package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vegetable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String productName;
    
    @Column(nullable = false)
    private BigDecimal pricePer100g;

    @Column(nullable=false)
    private Integer age;

    @OneToOne
    @JoinColumn(name="invnetoryId", unique = true, nullable=false)
    private Inventory inventory;

    
}
