package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenRequest {
    @NotNull(message = "El id del usuario es requerido")
    private Long userId;

    @NotEmpty(message = "El token es requerido")
    private String token;
}
