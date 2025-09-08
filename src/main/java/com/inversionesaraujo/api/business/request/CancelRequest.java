package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelRequest {
    @NotNull(message = "El id del pedido es requerido")
    private Long orderId;

    @NotEmpty(message = "La razón es requerida")
    private String reason;
}
