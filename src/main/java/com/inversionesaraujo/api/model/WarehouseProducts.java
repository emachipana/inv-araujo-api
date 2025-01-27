package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "warehouse_products")
public class WarehouseProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;
    @ManyToOne
    @NotNull(message = "El id del producto no puede ir vacío")
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @NotNull(message = "El id del almacén no puede ir vacío")
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}
