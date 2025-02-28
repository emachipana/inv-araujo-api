package com.inversionesaraujo.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "profits")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, unique = true)
    private String month;

    @Builder.Default
    @Column(nullable = false)
    private Double totalExpenses = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private Double income = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private Double profit = 0.0;
}
