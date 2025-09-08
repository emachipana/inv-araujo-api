package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferProductRequest {
    @NotNull(message = "El id del producto es requerido")
    private Long productId;

    @NotNull(message = "El id de la oferta es requerido")
    private Long offerId;

    private Long employeeId;
}
