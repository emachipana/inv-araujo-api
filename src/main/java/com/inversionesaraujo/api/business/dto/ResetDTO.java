package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;

import com.inversionesaraujo.api.model.Reset;
import com.inversionesaraujo.api.model.User;

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
    private Long userId;
    private String code;
    private LocalDateTime expiresAt;

    public static ResetDTO toDTO(Reset reset) {
        return ResetDTO
            .builder()
            .id(reset.getId())
            .userId(reset.getUser().getId())
            .code(reset.getCode())
            .expiresAt(reset.getExpiresAt())
            .build();
    }

    public static Reset toEntity(ResetDTO reset) {
        User user = new User();
        user.setId(reset.getUserId());

        return Reset
            .builder()
            .id(reset.getId())
            .user(user)
            .code(reset.getCode())
            .expiresAt(reset.getExpiresAt())
            .build();
    }
}
