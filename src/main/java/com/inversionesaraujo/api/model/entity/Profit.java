package com.inversionesaraujo.api.model.entity;

import java.util.List;
import java.time.LocalDateTime;

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
@Table(name = "profits")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @NotEmpty(message = "El id del admin no puede ir vacio")
    private Admin admin;
    @NotEmpty(message = "La fecha no puede ir vacia")
    private LocalDateTime date;
    @Column(nullable = false)
    private String month;
    @Column(nullable = false)
    private Double totalExpenses;
    @NotEmpty(message = "El monto del ingreso del mes no puede ir vacio")
    private Double income;
    @Column(nullable = false)
    private Double profit;
    @OneToMany(mappedBy = "profit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;
}
