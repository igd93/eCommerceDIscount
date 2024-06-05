package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Bread")

public class Bread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String productName;

    @Column(nullable=false)
    private BigDecimal pricePerUnit;

    @Column(nullable=false)
    private Integer age;

    @OneToOne
    @JoinColumn(name= "inventoryId", unique=true, nullable = false)
    private Inventory inventory;
    
}
