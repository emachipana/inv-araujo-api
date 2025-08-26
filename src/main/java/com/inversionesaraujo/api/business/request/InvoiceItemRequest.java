package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.ProductUnit;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceItemRequest {
    @NotNull(message = "El id del comprobante es requerido")
    private Long invoiceId;

    @NotEmpty(message = "El nombre del item es requerido")
    private String name;

    @NotNull(message = "La unidad del item es requerida")
    private ProductUnit unit;

    @NotNull(message = "El precio del item es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double price;

    @NotNull(message = "La cantidad del item es requerida")
    @Positive(message = "Debe ser mayor a 0")
    private Integer quantity;

    private Boolean isIgvApply = true;
}
