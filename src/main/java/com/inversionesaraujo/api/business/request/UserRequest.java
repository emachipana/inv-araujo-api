package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotNull(message = "El id de la imagen es requerida")
    Long imageId;

    @Size(min = 6)
    private String newPassword;
}
