package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Beer")

public class Beer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal pricePerUnit;

    @Column(nullable = false)
    private Boolean isPack;

    @OneToOne
    @JoinColumn(name="inventoryId", unique = true, nullable = false)
    private Inventory inventory;
    
}
