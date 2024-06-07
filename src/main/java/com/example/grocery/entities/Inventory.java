package com.example.grocery.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProductType", nullable = false)
    private String productType;

}
