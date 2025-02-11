package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {
    @NotNull(message = "El id del producto es requerido")
    private Long productId;

    @NotNull(message = "El precio de descuento es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double price;
}
