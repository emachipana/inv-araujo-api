package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientChatRequest {
    @NotEmpty(message = "La consulta es requerida")
    private String question;
}
