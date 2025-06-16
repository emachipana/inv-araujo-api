package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull(message = "El ID de reinicio es requerido")
    Long resetId;

    @NotEmpty(message = "El código es requerido")
    String code;

    @NotEmpty(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String newPassword;    
}
