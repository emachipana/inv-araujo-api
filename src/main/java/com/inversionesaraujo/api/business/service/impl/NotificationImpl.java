package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.INotification;
import com.inversionesaraujo.api.business.spec.NotificationSpecifications;
import com.inversionesaraujo.api.model.Notification;
import com.inversionesaraujo.api.repository.NotificationRepository;

@Service
public class NotificationImpl implements INotification {
    @Autowired
    private NotificationRepository notificationRepo;

    @Transactional
    @Override
    public Notification save(Notification notification) {
        return notificationRepo.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public Notification findById(Integer id) {
        return notificationRepo.findById(id).orElseThrow(() -> new DataAccessException("La notificación no existe") {});
    }

    @Transactional
    @Override
    public void delete(Notification notification) {
        notificationRepo.delete(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> findByUsername(String username) {
        Specification<Notification> spec = Specification.where(NotificationSpecifications.belongsToUser((username)));

        return notificationRepo.findAll(spec);
    }
}
