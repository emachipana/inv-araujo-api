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
@Table(name = "varieties")
public class Variety {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variety_seq")
    @SequenceGenerator(name = "variety_seq", sequenceName = "variety_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double minPrice;

    @ManyToOne
    @JoinColumn(name = "tuber_id", nullable = false)
    private Tuber tuber;

    @Column(nullable = false)
    private String name;
}
