package com.inversionesaraujo.api.model.entity;

import java.time.LocalDateTime;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "vitro_orders")
public class VitroOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El tipo de documento no puede ir vacio")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @NotEmpty(message = "El documento no puede ir vacio")
    @Size(max = 20)
    private String document;
    @NotEmpty(message = "Los nombres no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String firstName;
    @NotEmpty(message = "Los apellidos no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String lastName;
    @NotEmpty(message = "El destino no puede ir vacio")
    @Size(min = 3)
    private String destination;
    @Column(nullable = false)
    private Double total;
    @NotNull(message = "El adelanto no puede ir vacio")
    private Double advance;
    @Column(nullable = false)
    private Double pending;
    @NotNull(message = "La fecha de inicio no puede ir vacia")
    private LocalDateTime initDate;
    @NotNull(message = "La fecha de fin no puede ir vacia")
    private LocalDateTime finishDate;
    private LocalDateTime pickDate;
    @NotEmpty(message = "El telefono no puede ir vacio")
    @Size(max = 12)
    private String phone;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    @NotEmpty(message = "El id del comprobante no puede ir vacio")
    private Invoice invoice;
    @OneToMany(mappedBy = "vitroOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderVariety> varieties;
}
