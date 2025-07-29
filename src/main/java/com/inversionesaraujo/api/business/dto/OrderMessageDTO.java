package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.OrderMessage;

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
public class OrderMessageDTO {
    private Long id;
    private UserDTO sender;
    private UserDTO receiver;
    private Long orderId;
    private String message;
    private LocalDateTime createdAt;

    public static OrderMessageDTO toDTO(OrderMessage orderMessage) {
        return OrderMessageDTO.builder()
            .id(orderMessage.getId())
            .sender(UserDTO.toDTO(orderMessage.getSender()))
            .receiver(UserDTO.toDTO(orderMessage.getReceiver()))
            .orderId(orderMessage.getOrder().getId())
            .message(orderMessage.getMessage())
            .createdAt(orderMessage.getCreatedAt())
            .build();
    }

    public static OrderMessage toEntity(OrderMessageDTO orderMessageDTO) {
        Order order = new Order();
        order.setId(orderMessageDTO.getOrderId());

        return OrderMessage.builder()
            .id(orderMessageDTO.getId())
            .sender(UserDTO.toEntity(orderMessageDTO.getSender()))
            .receiver(UserDTO.toEntity(orderMessageDTO.getReceiver()))
            .order(order)
            .message(orderMessageDTO.getMessage())
            .createdAt(orderMessageDTO.getCreatedAt())
            .build();
    }

    public static List<OrderMessageDTO> toListDTO(List<OrderMessage> orderMessages) {
        return orderMessages
            .stream()
            .map(OrderMessageDTO::toDTO)
            .collect(Collectors.toList());
    }
}
