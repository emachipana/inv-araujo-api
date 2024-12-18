package com.inversionesaraujo.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "La ciudad no puede ir vacia")
    private String city;
    @NotEmpty(message = "El departamento no puede ir vacio")
    private String department;
    @NotEmpty(message = "El telefono no puede ir vacio")
    @Size(max = 12)
    private String phone;
    @NotEmpty(message = "El documento no puede ir vacio")
    @Size(max = 20)
    @Column(unique = true)
    private String document;
    @NotNull(message = "El tipo de documento no puede ir vacio")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Column(nullable = false)
    private Double consumption;
    @NotEmpty(message = "Los nombres no pueden ir vacios")
    @Size(min = 3)
    private String rsocial;
    @Column(nullable = false)
    @Builder.Default
    private String createdBy = Role.CLIENTE.toString();
    @NotEmpty(message = "El email no puede ir vacio")
    @Email(message = "El formato es incorrecto")
    @Column(unique = true)
    private String email;
    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("client")
    private List<Order> orders;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("client")
    private List<VitroOrder> vitroOrders;
}
