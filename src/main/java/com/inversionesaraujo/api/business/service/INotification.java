package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Notification;

public interface INotification {
    Notification save(Notification notification);
    
    Notification findById(Integer id);

    List<Notification> findByUsername(String username);

    void delete(Notification notification);
}
