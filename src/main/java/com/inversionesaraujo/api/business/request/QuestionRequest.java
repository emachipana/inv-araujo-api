package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    @NotEmpty(message = "La pregunta es requerida")
    @Size(min = 3, message = "La pregunta debe tener al menos 3 caracteres")
    private String question;
}
