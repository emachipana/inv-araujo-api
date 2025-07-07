package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseProductRequest {
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Deber ser mayor a 0")
    private Integer quantity;

    @NotNull(message = "El id del producto es requerido")
    private Long productId;

    @NotNull(message = "El id del almacén es requerido")
    private Long warehouseId;

    private Long employeeId;
}
