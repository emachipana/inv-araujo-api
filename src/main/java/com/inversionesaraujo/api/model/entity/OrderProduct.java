package com.inversionesaraujo.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotEmpty(message = "El id del pedido no puede ir vacio")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotEmpty(message = "El id del producto no puede ir vacio")
    private Product product;
    @NotEmpty(message = "La cantidad no puede ir vacia")
    private Integer quantity;
    @Column(nullable = false)
    private Double subTotal;
    @Column(nullable = false)
    private String productName;
}
