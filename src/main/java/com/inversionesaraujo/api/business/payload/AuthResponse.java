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
public class AuthResponse {
    private UserDTO user;
    private String token;    
}
    