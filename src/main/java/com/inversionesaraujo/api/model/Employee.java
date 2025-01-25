package com.inversionesaraujo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "La razón social no puede ir vacía")
    private String rsocial;
    @NotEmpty(message = "El documento no puede ir vacío")
    @Size(min = 8, max = 8)
    @Column(unique = true)
    private String document;
    @NotEmpty(message = "El correo no puede ir vacío")
    @Email(message = "El formato es incorrecto")
    @Column(unique = true)
    private String email;
    @NotEmpty(message = "El telefono no puede ir vacio")
    @Size(min = 9)
    private String phone;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
