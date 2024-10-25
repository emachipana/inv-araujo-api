package com.inversionesaraujo.api.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "vitro_orders")
public class VitroOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "El id del client no puede ir vacio")
    private Client client;
    @NotEmpty(message = "El departamento no puede ir vacio")
    private String department;
    @NotEmpty(message = "El departamento no puede ir vacio")
    private String city;
    @Column(nullable = false)
    @Builder.Default
    private Double total = 0.0;
    @Builder.Default
    private Double totalAdvance = 0.0;
    @Column(nullable = false)
    @Builder.Default
    private Double pending = 0.0;
    @NotNull(message = "La fecha de inicio no puede ir vacia")
    private LocalDate initDate;
    private LocalDate finishDate;
    private LocalDate pickDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDIENTE;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @OneToMany(mappedBy = "vitroOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderVariety> items;
    @OneToMany(mappedBy = "vitroOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Advance> advances;
}
