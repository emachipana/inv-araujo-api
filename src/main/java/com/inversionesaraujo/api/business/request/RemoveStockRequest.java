package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveStockRequest {
    @NotNull(message = "El id del producto es requerido")
    private Long productId;

    @NotNull(message = "La cantidad es requerida")
    private Integer quantity;

    @NotNull(message = "La razón es requerida")
    private String reason;

    @NotNull(message = "El id del empleado es requerido")
    private Long employeeId;
}
