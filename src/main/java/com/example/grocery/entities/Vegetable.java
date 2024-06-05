package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Vegetable")
public class Vegetable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String productName;
    
    @Column(nullable = false)
    private BigDecimal pricePer100g;    

    @OneToOne
    @JoinColumn(name="inventoryId", unique = true, nullable=false)
    private Inventory inventory;

    
}
