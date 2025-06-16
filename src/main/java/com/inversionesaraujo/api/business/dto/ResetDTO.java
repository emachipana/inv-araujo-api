package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;

import com.inversionesaraujo.api.model.Reset;

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
public class ResetDTO {
    private Long id;
    private String code;
    private String email;
    private LocalDateTime expiresAt;

    public static ResetDTO toDTO(Reset reset) {
        return ResetDTO
            .builder()
            .id(reset.getId())
            .code(reset.getCode())
            .expiresAt(reset.getExpiresAt())
            .email(reset.getEmail())
            .build();
    }

    public static Reset toEntity(ResetDTO reset) {
        return Reset
            .builder()
            .id(reset.getId())
            .code(reset.getCode())
            .expiresAt(reset.getExpiresAt())
            .email(reset.getEmail())
            .build();
    }
}
