package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiverInfoRequest {
    @NotEmpty(message = "El nombre completo es requerido")
    private String fullName;

    @NotEmpty(message = "El documento es requerido")
    private String document;

    @NotEmpty(message = "El telefono es requerido")
    private String phone;

    @NotEmpty(message = "El departamento es requerido")
    private String department;

    @NotEmpty(message = "La ciudad es requerida")
    private String city;
}
