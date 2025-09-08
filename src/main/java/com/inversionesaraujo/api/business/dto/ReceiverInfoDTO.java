package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.ReceiverInfo;

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
public class ReceiverInfoDTO {
    private String fullName;
    private String document;
    private String phone;
    private String code;
    private String trackingCode;

    public static ReceiverInfoDTO toDTO(ReceiverInfo item) {
        if(item == null) return null;

        return ReceiverInfoDTO
            .builder()
            .fullName(item.getFullName())
            .document(item.getDocument())
            .phone(item.getPhone())
            .code(item.getCode())
            .trackingCode(item.getTrackingCode())
            .build();
    }

    public static ReceiverInfo toEntity(ReceiverInfoDTO item) {
        if(item == null) return null;

        return ReceiverInfo
            .builder()
            .fullName(item.getFullName())
            .document(item.getDocument())
            .phone(item.getPhone())
            .code(item.getCode())
            .trackingCode(item.getTrackingCode())
            .build();
    }
}
