package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitroDeliveredRequest {
    @NotNull(message = "El id del empleado es requerido")
    private Long employeeId;

    @NotNull(message = "El id de la evidencia es requerido")
    private Long evidenceId;
}
