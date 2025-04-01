package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.NotificationType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationRequest {
    @NotNull(message = "El id del usuario es requerido")
    private Long userId;

    @NotNull(message = "El tipo de notificación es requerido")
    private NotificationType type;

    @NotEmpty(message = "La redirección es requerida")
    private String redirectTo;

    @Builder.Default
    private Boolean isRead = false;
}
