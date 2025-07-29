package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_variety_seq")
    @SequenceGenerator(name = "order_variety_seq", sequenceName = "order_variety_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vitro_order_id", nullable = false)
    private VitroOrder vitroOrder;

    @ManyToOne
    @JoinColumn(name = "variety_id", nullable = false)
    private Variety variety;
    
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double subTotal;
}
