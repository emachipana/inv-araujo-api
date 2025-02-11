package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.payload.AuthResponse;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;

public interface IAuth {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
