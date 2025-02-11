package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.model.Notification;
import com.inversionesaraujo.api.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private NotificationType type;
    private String message;
    private LocalDateTime createdAt;
    private Boolean isRead;
    private String redirectTo;

    public static NotificationDTO toDTO(Notification notification) {
        return NotificationDTO
            .builder()
            .id(notification.getId())
            .userId(notification.getUser().getId())
            .type(notification.getType())
            .message(notification.getMessage())
            .createdAt(notification.getCreatedAt())
            .isRead(notification.getIsRead())
            .redirectTo(notification.getRedirectTo())
            .build();
    }

    public static Notification toEntity(NotificationDTO notification) {
        User user = new User();
        user.setId(notification.getUserId());
        
        return Notification
            .builder()
            .id(notification.getId())
            .user(user)
            .type(notification.getType())
            .message(notification.getMessage())
            .createdAt(notification.getCreatedAt())
            .isRead(notification.getIsRead())
            .redirectTo(notification.getRedirectTo())
            .build();
    }

    public static List<NotificationDTO> toListDTO(List<Notification> notifications) {
        return notifications
            .stream()
            .map(NotificationDTO::toDTO)
            .collect(Collectors.toList());
    }
}
