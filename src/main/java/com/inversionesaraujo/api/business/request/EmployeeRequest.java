package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    @NotEmpty(message = "La razón social es requerida")
    private String rsocial;

    @NotEmpty(message = "El documento es requerido")
    private String document;

    @NotEmpty(message = "El correo es requerido")
    @Email(message = "El formato es incorrecto")
    private String email;
    
    @NotEmpty(message = "El teléfono es requerido")
    private String phone;

    private Long userId;
}
