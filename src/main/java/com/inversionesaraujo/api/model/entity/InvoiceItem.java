package com.inversionesaraujo.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "invoice_items")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @NotNull(message = "El id del comprobante no puede ir vacio")
    @JsonIgnore
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "El id del producto no puede ir vacio")
    private Product product;
    @NotNull(message = "La cantidad no puede ir vacia")
    private Integer quantity;
    @Column(nullable = false)
    private Double subTotal;
    @NotNull(message = "El precio no puede ir vacio")
    private Double price;
    @Column(nullable = false)
    @Builder.Default
    private boolean isIgvApply = true;
}
