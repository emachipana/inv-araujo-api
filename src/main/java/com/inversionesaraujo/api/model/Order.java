package com.inversionesaraujo.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private Status status = Status.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private OrderLocation location = OrderLocation.ALMACEN;

    @Builder.Default
    @Column(nullable = false)
    private Double total = 0.0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDate maxShipDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    @Builder.Default
    private String createdBy = "CLIENTE";

    private LocalDateTime deliveredAt;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "fullName", column = @Column(name = "receiver_full_name")),
        @AttributeOverride(name = "document", column = @Column(name = "receiver_document")),
        @AttributeOverride(name = "phone", column = @Column(name = "receiver_phone")),
        @AttributeOverride(name = "code", column = @Column(name = "receiver_code")),
        @AttributeOverride(name = "trackingCode", column = @Column(name = "receiver_tracking_code"))
    })
    private ReceiverInfo receiverInfo;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "hour", column = @Column(name = "pickup_hour")),
        @AttributeOverride(name = "date", column = @Column(name = "pickup_date"))
    })
    private PickupInfo pickupInfo;
}
