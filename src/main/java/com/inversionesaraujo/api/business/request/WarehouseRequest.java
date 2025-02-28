package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseRequest {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 2)
    private String name;

    @NotEmpty(message = "El departamento es requerido")
    @Size(min = 3)
    private String department;

    @NotEmpty(message = "La provincia es requerida")
    @Size(min = 3)
    private String province;

    @NotEmpty(message = "El distrito es requerido")
    @Size(min = 3)
    private String district;

    @NotEmpty(message = "La dirección es requerida")
    @Size(min = 3)
    private String address;

    @NotEmpty(message = "La referencia es requerida")
    @Size(min = 3)
    private String ref;
}
