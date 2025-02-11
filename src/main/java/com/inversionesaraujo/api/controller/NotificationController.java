package com.inversionesaraujo.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.NotificationDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.model.NotificationType;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private INotification notiService;

    @GetMapping("getByUser")
    public ResponseEntity<MessageResponse> getAllByUserId(Authentication auth) {
        List<NotificationDTO> notifications = notiService.findByUsername(auth.getName());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(notifications)
            .message("Las notificaciones se encontraron con éxito")
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid NotificationRequest request) {
        Map<NotificationType, String> messages = Map.of(
            NotificationType.NEW_VITRO_ORDER, "Nuevo pedido invitro",
            NotificationType.NEW_ORDER, "Nuevo pedido",
            NotificationType.NEW_CONTACT_MESSAGE, "Nueva mensaje de contacto",
            NotificationType.NEW_USER, "Nuevo usuario registrado",
            NotificationType.PROX_VITRO_ORDER, "Pedido invitro se entrega mañana",
            NotificationType.PROX_ORDER, "Pedido se entrega mañana",
            NotificationType.NEW_ORDER_MESSAGE, "Nuevo mensaje de un pedido",
            NotificationType.NEW_VITROORDER_MESSAGE, "Nueva mensaje de un pedido invitro"
        );
        String message = messages.get(request.getType());

        NotificationDTO notification = notiService.save(NotificationDTO
            .builder()
            .type(request.getType())
            .userId(request.getUserId())
            .message(message)
            .redirectTo(request.getRedirectTo())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .data(notification)
            .message("La notificación se creó con éxito")
            .build());
    }
}
