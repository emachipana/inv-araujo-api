package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.inversionesaraujo.api.business.request.UserTokenRequest;
import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.business.service.IUserToken;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private INotification notiService;
    @Autowired
    private IUserToken tokenService;

    @PostMapping("/register-token")
    public ResponseEntity<MessageResponse> registerToken(@RequestBody @Valid UserTokenRequest request) {
        System.out.println(request.getToken());
        tokenService.saveOrUpdateToken(request.getUserId(), request.getToken());
        
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Token registrado correctamente")
            .build());
    }

    @GetMapping("/getByUser")
    public ResponseEntity<MessageResponse> getAllByUserId(Authentication auth) {
        List<NotificationDTO> notifications = notiService.findByUsername(auth.getName());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(notifications)
            .message("Las notificaciones se encontraron con éxito")
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> markAsRead(@PathVariable Long id) {
        NotificationDTO notification = notiService.findById(id);
        notification.setIsRead(true);
        notiService.save(notification);

        return ResponseEntity.status(200).body(MessageResponse
            .builder()
            .data(notification)
            .message("La notificación se actualizo con éxito")
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid NotificationRequest request) {
        NotificationDTO notification = notiService.create(request);

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .data(notification)
            .message("La notificación se creó con éxito")
            .build());
    }
}
