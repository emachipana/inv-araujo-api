package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.model.dao.ClientDao;
import com.inversionesaraujo.api.model.dao.UserDao;
import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.AuthResponse;
import com.inversionesaraujo.api.model.request.LoginRequest;
import com.inversionesaraujo.api.model.request.RegisterRequest;
import com.inversionesaraujo.api.service.IAuth;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthImpl implements IAuth {
    private final UserDao userDao;
    private final JwtImpl jwtService;
    private final ClientDao clientDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userDao.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponse
            .builder()
            .token(token)
            .user(user)
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Client client = clientDao.findById(request.getClientId()).orElseThrow(() -> new DataAccessException("El client no existe") {});
        UserDetails newUser = userDao.save(User
            .builder()
            .client(client)
            .username(client.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build());
        String token = jwtService.getToken(newUser);

        return AuthResponse
            .builder()
            .token(token)
            .user(newUser)
            .build();
    }
}
