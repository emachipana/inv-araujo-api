package com.inversionesaraujo.api.business.payload;

import com.inversionesaraujo.api.business.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAuthResponse {
    private UserDTO user;
    private String token;
    private String action;
    private String rsocial;
    private String email;
}
