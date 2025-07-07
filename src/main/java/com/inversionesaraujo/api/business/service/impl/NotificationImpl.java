package com.inversionesaraujo.api.business.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.inversionesaraujo.api.business.dto.NotificationDTO;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.business.spec.NotificationSpecifications;
import com.inversionesaraujo.api.model.Notification;
import com.inversionesaraujo.api.model.NotificationType;
import com.inversionesaraujo.api.model.Permission;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.NotificationRepository;
import com.inversionesaraujo.api.repository.UserTokenRepository;
import com.inversionesaraujo.api.repository.UserRepository;

@Service
public class NotificationImpl implements INotification {
    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private UserTokenRepository tokenRepo;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public NotificationDTO save(NotificationDTO notification) {
        Notification notificationSaved = notificationRepo.save(NotificationDTO.toEntity(notification));

        return NotificationDTO.toDTO(notificationSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationDTO findById(Long id) {
        Notification notification = notificationRepo.findById(id).orElseThrow(() -> new DataAccessException("La notificación no existe") {});

        return NotificationDTO.toDTO(notification);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        notificationRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationDTO> findByUsername(String username) {
        Specification<Notification> spec = Specification.where(NotificationSpecifications.belongsToUser((username)));

        List<Notification> notifications = notificationRepo.findAll(spec);

        return NotificationDTO.toListDTO(notifications);
    }

    @Override
    public void sendNotification(NotificationDTO notification) {
        String destination = String.format("/topic/notifications/%d", notification.getUserId());
        template.convertAndSend(destination, notification);
        
        System.out.println("Notificación enviada a: " + destination);
    }

    public void sendPushNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                .setToken(token)
                .setNotification(com.google.firebase.messaging.Notification
                    .builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .build();

            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            System.out.println("Error enviando notificación FCM: " + e.getMessage());
        }
    }

    @Override
    public NotificationDTO create(NotificationRequest request) {
       String message = getMessage(request.getType());
        LocalDateTime today = LocalDateTime.now();
        NotificationDTO prev = NotificationDTO
            .builder()
            .type(request.getType())
            .userId(request.getUserId())
            .message(message)
            .redirectTo(request.getRedirectTo())
            .createdAt(today)
            .isRead(false)
            .build();

        Notification notification = notificationRepo.save(NotificationDTO.toEntity(prev));
        sendNotification(NotificationDTO.toDTO(notification));

        String userToken = tokenRepo.findLastToken();
        if(userToken != null) {
            sendPushNotification(userToken, message, message);
        }

        return NotificationDTO.toDTO(notification);
    }

    @Override
    @Transactional
    public void sendNotificationToUsersWithPermission(NotificationRequest request, Permission permission, Long excludeUserId) {
        List<User> users = userRepository.findUsersWithPermissionExcludingUser(permission, excludeUserId);
        
        String message = getMessage(request.getType());

        for (User user : users) {
            NotificationDTO notification = NotificationDTO
                .builder()
                .userId(user.getId())
                .type(request.getType())
                .message(getMessage(request.getType()))
                .redirectTo(request.getRedirectTo())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
            
            Notification savedNotification = notificationRepo.save(NotificationDTO.toEntity(notification));
            sendNotification(NotificationDTO.toDTO(savedNotification));
            
            String userToken = tokenRepo.findLastToken();
            if(userToken != null) {
                sendPushNotification(userToken, message, message);
            }
        }
    }

    private String getMessage(NotificationType type) {
        Map<NotificationType, String> messages = Map.of(
            NotificationType.NEW_VITRO_ORDER, "Nuevo pedido invitro recibido",
            NotificationType.NEW_ORDER, "Nuevo pedido recibido",
            NotificationType.NEW_CONTACT_MESSAGE, "Nueva mensaje de contacto",
            NotificationType.NEW_USER, "Nuevo usuario registrado",
            NotificationType.PROX_VITRO_ORDER, "Pedido invitro se entrega mañana",
            NotificationType.PROX_ORDER, "Pedido se entrega mañana",
            NotificationType.NEW_ORDER_MESSAGE, "Nuevo mensaje de un pedido",
            NotificationType.NEW_VITROORDER_MESSAGE, "Nueva mensaje de un pedido invitro"
        );

        return messages.get(type);
    }
}
