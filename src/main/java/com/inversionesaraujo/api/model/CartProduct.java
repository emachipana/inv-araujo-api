package com.inversionesaraujo.api.model;

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
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_products")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "El id del producto no puede ir vacío")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @NotNull(message = "El id del carrito no puede ir vacío")
    @JsonIgnore
    private Cart cart;
    @NotNull(message = "La cantidad no puede ir vacía")
    private Integer quantity;
    @NotNull(message = "El precio no puede ir vacío")
    private Double price;
    @Column(nullable = false)
    private Double subTotal;
}
