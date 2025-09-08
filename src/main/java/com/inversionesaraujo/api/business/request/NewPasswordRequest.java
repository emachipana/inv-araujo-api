package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequest {
    @NotEmpty(message = "La contraseña actual es requerida")
    @Size(min = 6, message = "La contraseña actual debe tener al menos 6 caracteres")
    private String currentPassword;
    
    @NotEmpty(message = "La contraseña nueva es requerida")
    @Size(min = 6, message = "La contraseña nueva debe tener al menos 6 caracteres")
    private String newPassword;
}
