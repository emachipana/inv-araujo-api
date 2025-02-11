package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.NotificationDTO;

public interface INotification {
    NotificationDTO save(NotificationDTO notification);
    
    NotificationDTO findById(Long id);

    List<NotificationDTO> findByUsername(String username);

    void delete(Long id);
}
