package com.inversionesaraujo.api.model;

import java.util.List;

import com.google.auto.value.AutoValue.Builder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El nombre no puede ir vacío")
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "El departamento no puede ir vacío")
    private String department;
    @NotEmpty(message = "La provincia no puede ir vacía")
    private String province;
    @NotEmpty(message = "El distrito no puede ir vacío")
    private String district;
    @NotEmpty(message = "La dirección no puede ir vacía")
    private String address;
    @NotEmpty(message = "La referencia no puede ir vacía")
    private String ref;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WarehouseProducts> products;
}
