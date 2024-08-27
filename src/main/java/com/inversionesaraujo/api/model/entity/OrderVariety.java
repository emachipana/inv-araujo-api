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
@Table(name = "order_varieties")
public class OrderVariety {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "vitro_order_id")
    @NotEmpty(message = "El id del pedido invitro no puede ir vacio")
    private VitroOrder vitroOrder;
    @ManyToOne
    @JoinColumn(name = "variety_id")
    @NotEmpty(message = "El id de la variedad no puede ir vacia")
    private Variety variety;    
    @NotEmpty(message = "El precio no puede ir vacio")
    private Double price;
    @NotEmpty(message = "La cantidad no puede ir vacia")
    private Integer quantity;
    @Column(nullable = false)
    private Double subTotal;
}
