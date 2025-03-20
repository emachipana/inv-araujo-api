package com.inversionesaraujo.api.model;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String city;

    @Builder.Default
    @Column(nullable = false)
    private Double total = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private Double totalAdvance = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private Double pending = 0.0;

    @Column(nullable = false)
    private LocalDate initDate;

    private LocalDate finishDate;
    private LocalDate pickDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private Status status = Status.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private OrderLocation location = OrderLocation.ALMACEN;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isReady = false;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @OneToMany(mappedBy = "vitroOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderVariety> items;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Builder.Default
    private Image image = null;
}
