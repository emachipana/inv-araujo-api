package com.inversionesaraujo.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "varieties")
public class Variety {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "El precio no puede ir vacio")
    private Double price;
    @NotNull(message = "El precio mínimo no puede ir vacio")
    private Double minPrice;
    @ManyToOne
    @JoinColumn(name = "tuber_id")
    @NotNull(message = "El id del tuberculo no pueder ir vacio")
    @JsonIgnore
    private Tuber tuber;
    @NotEmpty(message = "El nombre no puede ir vacio")
    private String name;
}
