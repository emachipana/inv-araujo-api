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
@Table(name = "order_varieties")
public class OrderVariety {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "vitro_order_id")
    @NotNull(message = "El id del pedido invitro no puede ir vacio")
    @JsonIgnore
    private VitroOrder vitroOrder;
    @ManyToOne
    @JoinColumn(name = "variety_id")
    @NotNull(message = "El id de la variedad no puede ir vacia")
    private Variety variety;    
    @NotNull(message = "El precio no puede ir vacio")
    private Double price;
    @NotNull(message = "La cantidad no puede ir vacia")
    private Integer quantity;
    @Column(nullable = false)
    private Double subTotal;
}
