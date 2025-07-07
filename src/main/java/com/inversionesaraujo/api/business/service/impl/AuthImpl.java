package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.AuthResponse;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;
import com.inversionesaraujo.api.business.service.IAuth;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.ClientRepository;
import com.inversionesaraujo.api.repository.UserRepository;

@Service
public class AuthImpl implements IAuth {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtImpl jwtService;
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private RoleImpl roleService;

    @Override
    public AuthResponse login(LoginRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        System.out.println(token);

        return AuthResponse
            .builder()
            .token(token)
            .user(UserDTO.toDTO(user))
            .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Client client = clientRepo.findById(request.getClientId()).orElseThrow(() -> new DataAccessException("El client no existe") {});
        RoleDTO role = roleService.findByName("CLIENTE");
        
        User newUser = userRepo.save(User
            .builder()
            .client(client)
            .username(client.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(RoleDTO.toEntity(role))
            .build());
        String token = jwtService.getToken(newUser);

        return AuthResponse
            .builder()
            .token(token)
            .user(UserDTO.toDTO(newUser))
            .build();
    }
}
