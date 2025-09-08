package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.CancelOrderRequest;
import com.inversionesaraujo.api.model.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelOrderRequestDTO {
    private Long id;
    private String reason;
    private Long orderId;
    private Boolean accepted;
    private Boolean rejected;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CancelOrderRequestDTO toDTO(CancelOrderRequest cancelOrderRequest) {
        if(cancelOrderRequest == null) return null;
        
        return CancelOrderRequestDTO
            .builder()
            .id(cancelOrderRequest.getId())
            .reason(cancelOrderRequest.getReason())
            .orderId(cancelOrderRequest.getOrder().getId())
            .accepted(cancelOrderRequest.getAccepted())
            .rejected(cancelOrderRequest.getRejected())
            .createdAt(cancelOrderRequest.getCreatedAt())
            .updatedAt(cancelOrderRequest.getUpdatedAt())
            .build();
    }

    public static CancelOrderRequest toEntity(CancelOrderRequestDTO cancelOrderRequestDTO) {
        if(cancelOrderRequestDTO == null) return null;

        Order order = new Order();
        order.setId(cancelOrderRequestDTO.getOrderId());

        return CancelOrderRequest
            .builder()
            .id(cancelOrderRequestDTO.getId())
            .reason(cancelOrderRequestDTO.getReason())
            .order(order)
            .accepted(cancelOrderRequestDTO.getAccepted())
            .rejected(cancelOrderRequestDTO.getRejected())
            .createdAt(cancelOrderRequestDTO.getCreatedAt())
            .updatedAt(cancelOrderRequestDTO.getUpdatedAt())
            .build();
    }

    public static List<CancelOrderRequestDTO> toListDTO(List<CancelOrderRequest> cancelOrderRequests) {
        return cancelOrderRequests
            .stream()
            .map(CancelOrderRequestDTO::toDTO)
            .collect(Collectors.toList());
    }
}
