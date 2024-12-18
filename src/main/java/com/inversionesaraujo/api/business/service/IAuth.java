package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.payload.AuthResponse;
import com.inversionesaraujo.api.business.dto.request.LoginRequest;
import com.inversionesaraujo.api.business.dto.request.RegisterRequest;

public interface IAuth {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
