package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 3)
    private String name;

    @NotEmpty(message = "La descripción es requerida")
    @Size(min = 10)
    private String description;

    private Long imageId;

    private Long employeeId;
}
