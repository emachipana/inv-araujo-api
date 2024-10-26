package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.model.dao.ClientDao;
import com.inversionesaraujo.api.model.dao.UserDao;
import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.AuthResponse;
import com.inversionesaraujo.api.model.payload.UserResponse;
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
        User user = userDao.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        UserResponse userResponse = UserResponse
            .builder()
            .id(user.getId())
            .image(user.getImage())
            .name(user.getAdmin() != null ? user.getAdmin().getFirstName() : user.getClient().getRsocial())
            .lastName(user.getAdmin() != null ? user.getAdmin().getLastName() : "")
            .role(user.getRole())
            .username(user.getUsername())
            .build();

        return AuthResponse
            .builder()
            .token(token)
            .user(userResponse)
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Client client = clientDao.findById(request.getClientId()).orElseThrow(() -> new DataAccessException("El client no existe") {});
        User newUser = userDao.save(User
            .builder()
            .client(client)
            .username(client.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build());
        String token = jwtService.getToken(newUser);
        UserResponse userResponse = UserResponse
            .builder()
            .id(newUser.getId())
            .image(newUser.getImage())
            .name(newUser.getAdmin() != null ? newUser.getAdmin().getFirstName() : newUser.getClient().getRsocial())
            .lastName(newUser.getAdmin() != null ? newUser.getAdmin().getLastName() : "")
            .role(newUser.getRole())
            .username(newUser.getUsername())
            .build();

        return AuthResponse
            .builder()
            .token(token)
            .user(userResponse)
            .build();
    }
}
