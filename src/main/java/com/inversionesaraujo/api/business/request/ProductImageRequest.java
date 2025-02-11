package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageRequest {
    @NotNull(message = "El id de la imagen es requerida")
    private Long imageId;

    @NotNull(message = "El id del producto es requerido")
    private Long productId;
}
