package com.example.grocery.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class QuantityDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventoryId", nullable = false)
    private Inventory inventory;

    @Column(nullable=false)
    private Integer quantity;

    @Column(nullable=false)
    private BigDecimal discountAmount;
    
}
