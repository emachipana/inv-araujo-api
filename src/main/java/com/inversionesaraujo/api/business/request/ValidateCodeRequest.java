package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCodeRequest {
    @NotEmpty(message = "El código es requerido")
    public String code;

    @NotNull(message = "El ID de reinicio es requerido")
    public Long resetId;    
}
