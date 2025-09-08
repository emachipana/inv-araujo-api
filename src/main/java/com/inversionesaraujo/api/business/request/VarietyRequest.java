package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VarietyRequest {
    @NotNull(message = "El precio es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El precio mínimo es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double minPrice;

    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 2)
    private String name;

    @NotNull(message = "El id del tubérculo es requerido")
    private Long tuberId;

    private Long employeeId;
}
