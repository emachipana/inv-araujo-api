package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.Origin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 5)
    private String fullName;

    @NotEmpty(message = "El teléfono es requerido")
    @Size(min = 6)
    private String phone;

    @NotEmpty(message = "El asunto es requerido")
    @Size(min = 3)
    private String subject;

    @NotEmpty(message = "El contenido es requerido")
    @Size(min = 10)
    private String content;

    @NotNull(message = "El origen del mensaje es requerido")
    private Origin origin;

    @NotEmpty(message = "El correo es requerido")
    @Email(message = "El formato es incorrecto")
    private String email;
}
