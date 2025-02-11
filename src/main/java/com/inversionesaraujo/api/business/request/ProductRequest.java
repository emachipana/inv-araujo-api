package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 3)
    private String name;

    @NotEmpty(message = "La descripción es requerida")
    @Size(min = 10)
    private String description;

    @NotEmpty(message = "La marca es requerida")
    @Size(min = 3)
    private String brand;

    @NotEmpty(message = "La unidad de medida es requerida")
    private String unit;

    @NotNull(message = "El precio de compra es requerido")
    @Positive(message = "Deber ser mayor a 0")
    private Double purchasePrice;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "Deber ser mayor a 0")
    private Double price;

    @NotNull(message = "El id de la categoría es requerido")
    private Long categoryId;

    private Boolean isActive = true;
}
