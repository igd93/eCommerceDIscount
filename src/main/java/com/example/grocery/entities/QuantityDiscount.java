package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "QuantityDiscount")

public class QuantityDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventoryId", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "BeerId", nullable = false)
    private Beer beer;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, scale = 8, precision = 2)
    private BigDecimal discountAmount;

}
