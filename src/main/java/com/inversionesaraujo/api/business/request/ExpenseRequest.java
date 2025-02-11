package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.ExpenseType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseRequest {
    @NotEmpty(message = "El nombre es requerido")
    private String name;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double price;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Debe ser mayor a 0")
    private Integer quantity;

    @NotNull(message = "El id del registro de ingresos es requerido")
    private Long profitId;

    @NotNull(message = "El tipo de gasto es requerido")
    private ExpenseType type;
}
