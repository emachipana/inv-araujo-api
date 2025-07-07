package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.OrderMessageDTO;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.repository.OrderMessageRepository;
import com.inversionesaraujo.api.business.service.IOrderMessage;
import com.inversionesaraujo.api.model.NotificationType;

@Service
public class OrderMessageImpl implements IOrderMessage {
    @Autowired
    private OrderMessageRepository repo;
    @Autowired
    private SimpMessagingTemplate messagingService;
    @Autowired
    private NotificationImpl notiService;

    @Override
    public OrderMessageDTO save(OrderMessageDTO orderMessageDTO) {
        return OrderMessageDTO.toDTO(repo.save(OrderMessageDTO.toEntity(orderMessageDTO)));
    }

    @Override
    public void alertNewMessage(OrderMessageDTO orderMessageDTO) {
        messagingService.convertAndSend(
            String.format("/topic/orders/%d/chat", orderMessageDTO.getOrderId()),
            orderMessageDTO
        );

        NotificationRequest notiRequest = NotificationRequest
            .builder()
            .userId(orderMessageDTO.getReceiver().getId())
            .type(NotificationType.NEW_ORDER_MESSAGE)
            .redirectTo(String.format("/pedidos/%d/chat", orderMessageDTO.getOrderId()))
            .build();

        notiService.create(notiRequest);
    }

    @Override
    public List<OrderMessageDTO> findByOrderId(Long orderId) {
        return OrderMessageDTO.toListDTO(repo.findByOrderId(orderId));
    }
    
}
