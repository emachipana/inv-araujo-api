package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductRequest {
    @NotNull(message = "El id del pedido es requerido")
    private Long orderId;

    @NotNull(message = "El id del producto es requerido")
    private Long productId;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Deber ser mayor a 0")
    private Integer quantity;

    private Long employeeId;
}
