package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.NotificationDTO;
import com.inversionesaraujo.api.business.request.NotificationRequest;

public interface INotification {
    NotificationDTO save(NotificationDTO notification);
    
    NotificationDTO findById(Long id);

    List<NotificationDTO> findByUsername(String username);

    void sendNotification(NotificationDTO notification);

    NotificationDTO create(NotificationRequest request);

    void delete(Long id);
}
