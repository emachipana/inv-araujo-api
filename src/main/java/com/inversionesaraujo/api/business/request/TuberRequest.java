package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TuberRequest {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 2)
    private String name;
}
