package com.inversionesaraujo.api.model.entity;

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
@Table(name = "landing_offer_products")
public class LandingOfferProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    @NotEmpty(message = "El id del grupo de ofertas no puede ir vacio")
    private LandingOffer landingOffer;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotEmpty(message = "El id del producto no puede ir  vacio")
    private Product product;
}
