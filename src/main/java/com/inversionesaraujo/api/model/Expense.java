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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El nombre no puede ir vacio")
    @Size(min = 3, max = 200)
    private String name;
    @NotNull(message = "El precio no puede ir vacio")
    private Double price;
    @NotNull(message = "La cantidad no puede ir vacia")
    private Integer quantity;
    @Column(nullable = false)
    private Double subTotal;
    @ManyToOne
    @JoinColumn(name = "profit_id")
    @NotNull(message = "El id del registro de ingresos no puede ir vacio")
    @JsonIgnore
    private Profit profit;
}
