package com.inversionesaraujo.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El titulo no puede ir vacio")
    @Size(min = 3, max = 100)
    private String title;
    @NotEmpty(message = "La descripcion no puede ir vacia")
    @Size(min = 3, max = 100)
    private String description;
    @NotEmpty(message = "La palabra a resaltar no puede ir vacia")
    private String markedWord;
    @Column(nullable = false)
    @Builder.Default
    private boolean isUsed = false;
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OfferProduct> products;
}
