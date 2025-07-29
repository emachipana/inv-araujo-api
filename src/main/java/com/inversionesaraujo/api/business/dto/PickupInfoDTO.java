package com.inversionesaraujo.api.business.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.inversionesaraujo.api.model.PickupInfo;

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
public class PickupInfoDTO {
    private LocalDate date;
    private LocalTime hour;

    public static PickupInfoDTO toDTO(PickupInfo pickupInfo) {
        if(pickupInfo == null) return null;

        return PickupInfoDTO
            .builder()
            .date(pickupInfo.getDate())
            .hour(pickupInfo.getHour())
            .build();
    }

    public static PickupInfo toEntity(PickupInfoDTO pickupInfoDTO) {
        if(pickupInfoDTO == null) return null;

        return PickupInfo
            .builder()
            .date(pickupInfoDTO.getDate())
            .hour(pickupInfoDTO.getHour())
            .build();
    }
}
