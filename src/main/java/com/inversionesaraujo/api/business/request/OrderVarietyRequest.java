package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVarietyRequest {
    @NotNull(message = "El id del pedido es requerido")
    private Long vitroOrderId;

    @NotNull(message = "El id de la variedad es requerida")
    private Long varietyId;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "Deber ser mayor a 0")
    private Double price;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Deber ser mayor a 0")
    private Integer quantity;

    private Long employeeId;
}
