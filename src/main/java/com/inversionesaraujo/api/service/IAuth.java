package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.payload.AuthResponse;
import com.inversionesaraujo.api.model.request.LoginRequest;
import com.inversionesaraujo.api.model.request.RegisterRequest;

public interface IAuth {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
