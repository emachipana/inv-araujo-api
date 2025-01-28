package com.inversionesaraujo.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "warehouse_changes")
public class WarehouseChanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "La razón del cambio no puede ir vacía")
    private String reason;
    @ManyToOne
    @NotNull(message = "El id del almacen de origen no puede ir vacío")
    @JoinColumn(name = "from_warehouse_id")
    private Warehouse from;
    @ManyToOne
    @NotNull(message = "El id del almacen de cambio no puede ir vacío")
    @JoinColumn(name = "to_warehouse_id")
    private Warehouse to;
    @ManyToOne
    @NotNull(message = "El id del empleado no puede ir vacío")
    @JoinColumn(name = "employee_id")
    private Employee changedBy;
    @ManyToOne
    @NotNull(message = "El id del producto no puede ir vacío")
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime changeDate = LocalDateTime.now();
}
