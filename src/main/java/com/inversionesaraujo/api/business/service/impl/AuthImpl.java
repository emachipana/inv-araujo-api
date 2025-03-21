package com.inversionesaraujo.api.business.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.AuthResponse;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;
import com.inversionesaraujo.api.business.service.IAuth;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.ClientRepository;
import com.inversionesaraujo.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthImpl implements IAuth {
    private final UserRepository userRepo;
    private final JwtImpl jwtService;
    private final ClientRepository clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponse
            .builder()
            .token(token)
            .user(UserDTO.toDTO(user))
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Client client = clientRepo.findById(request.getClientId()).orElseThrow(() -> new DataAccessException("El client no existe") {});
        User newUser = userRepo.save(User
            .builder()
            .client(client)
            .username(client.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build());
        String token = jwtService.getToken(newUser);

        return AuthResponse
            .builder()
            .token(token)
            .user(UserDTO.toDTO(newUser))
            .build();
    }
}
