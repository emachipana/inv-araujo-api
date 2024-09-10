package com.inversionesaraujo.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "offerProducts")
public class OfferProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    @NotNull(message = "El id del grupo de ofertas no puede ir vacio")
    @JsonIgnore
    private Offer offer;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "El id del producto no puede ir  vacio")
    private Product product;
}
