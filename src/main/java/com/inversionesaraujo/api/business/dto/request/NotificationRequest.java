package com.inversionesaraujo.api.business.dto.request;

import com.inversionesaraujo.api.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Integer userId;
    private NotificationType type;
    private Boolean isRead;
}
