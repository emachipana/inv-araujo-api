package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.GenerateCodeAction;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateCodeRequest {
    @NotEmpty(message = "El email es requerido")
    @Email(message = "El fomato es inválido")
    String email;

    @NotNull(message = "La acción es requerida")
    GenerateCodeAction action;
}
