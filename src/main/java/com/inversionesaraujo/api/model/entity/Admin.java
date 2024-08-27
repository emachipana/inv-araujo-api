package com.inversionesaraujo.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "admins", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Los nombres no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String firstName;
    @NotEmpty(message = "Los apellidos no pueden ir vacios")
    @Size(min = 3, max = 100)
    private String lastName;
    @NotEmpty(message = "El email no puede ir vacio")
    @Email(message = "El formato es incorrecto")
    private String email;
    @Column(nullable = false)
    private Double totalProfit;
    @OneToOne(mappedBy = "admin")
    private User user;
}
