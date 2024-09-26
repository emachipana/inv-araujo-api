package com.inversionesaraujo.api.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "profits")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @NotNull(message = "El id del admin no puede ir vacio")
    @JsonIgnore
    private Admin admin;
    @NotNull(message = "La fecha no puede ir vacia")
    private LocalDate date;
    @Column(nullable = false)
    private String month;
    @Column(nullable = false)
    @Builder.Default
    private Double totalExpenses = 0.0;
    @Column(nullable = false)
    @Builder.Default
    private Double income = 0.0;
    @Column(nullable = false)
    @Builder.Default
    private Double profit = 0.0;
    @OneToMany(mappedBy = "profit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;
}
