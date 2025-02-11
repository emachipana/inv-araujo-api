package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitRequest {
    private LocalDate date;

    @NotNull(message = "El ingreso es requerido")
    @Positive(message = "Deber ser mayor a 0")
    private Double income;
}
