package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductRequest {
    @NotNull(message = "El id del producto es requerido")
    Long productId;

    @NotNull(message = "El id del carrito es requerido")
    Long cartId;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "Debe ser mayor a 0")
    Integer quantity;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "Debe ser mayor a 0")
    Double price;
}  
