package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Advance;
import com.inversionesaraujo.api.model.PaymentType;
import com.inversionesaraujo.api.model.VitroOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceDTO {
    private Long id;
    private Long vitroOrderId;
    private Double amount;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static AdvanceDTO toDTO(Advance advance) {
        return AdvanceDTO
            .builder()
            .id(advance.getId())
            .vitroOrderId(advance.getVitroOrder().getId())
            .amount(advance.getAmount())
            .paymentType(advance.getPaymentType())
            .createdAt(advance.getCreatedAt())
            .updatedAt(advance.getUpdatedAt())
            .build();
    }

    public static Advance toEntity(AdvanceDTO advance) {
        VitroOrder order = new VitroOrder();
        order.setId(advance.getVitroOrderId());

        return Advance
            .builder()
            .id(advance.getId())
            .vitroOrder(order)
            .amount(advance.getAmount())
            .paymentType(advance.getPaymentType())
            .createdAt(advance.getCreatedAt())
            .updatedAt(advance.getUpdatedAt())
            .build();
    }

    public static List<AdvanceDTO> toDTOList(List<Advance> advances) {
        return advances
            .stream()
            .map(AdvanceDTO::toDTO)
            .collect(Collectors.toList());
    }
}
