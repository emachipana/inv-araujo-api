package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferRequest {
    @NotEmpty(message = "El titulo es requerido")
    @Size(min = 3)
    private String title;

    @NotEmpty(message = "La descripción es requerida")
    @Size(min = 10)
    private String description;


    @NotEmpty(message = "La palabra resaltada es requerida")
    private String markedWord;

    private Boolean isUsed = false;
}
