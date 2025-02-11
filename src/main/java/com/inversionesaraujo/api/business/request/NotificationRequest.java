package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.NotificationType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    @NotNull(message = "El id del usuario es requerido")
    private Long userId;

    @NotNull(message = "El tipo de notificación es requerido")
    private NotificationType type;

    @NotEmpty(message = "La redirección es requerida")
    private String redirectTo;

    private Boolean isRead = false;
}
