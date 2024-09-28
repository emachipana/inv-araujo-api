package com.inversionesaraujo.api.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "clients", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "La direccion no puede ir vacia")
    @Size(min = 3)
    private String address;
    @NotEmpty(message = "El departamento no puede ir vacio")
    @Size(min = 3, max = 50)
    private String department;
    @NotEmpty(message = "La ciudad no puede ir vacia")
    @Size(min = 3, max = 50)
    private String city;
    @NotEmpty(message = "El telefono no puede ir vacio")
    @Size(max = 12)
    private String phone;
    @NotEmpty(message = "El documento no puede ir vacio")
    @Size(max = 20)
    private String document;
    @NotNull(message = "El tipo de documento no puede ir vacio")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Column(nullable = false)
    private Double consumption;
    @NotEmpty(message = "Los nombres no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String firstName;
    @NotEmpty(message = "Los apellidos no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String lastName;
    @NotEmpty(message = "El email no puede ir vacio")
    @Email(message = "El formato es incorrecto")
    private String email;
    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;
}
