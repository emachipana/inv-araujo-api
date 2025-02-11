package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.NotificationDTO;
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
}
