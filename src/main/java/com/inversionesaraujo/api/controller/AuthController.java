package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.payload.AuthResponse;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.LoginRequest;
import com.inversionesaraujo.api.model.request.RegisterRequest;
import com.inversionesaraujo.api.service.IAuth;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private IAuth authService;

    @PostMapping("login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse newLogin = authService.login(request);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El inicio de sesion se completo con exito")
                .data(newLogin)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse newRegister = authService.register(request);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro se completo con exito")
                .data(newRegister)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
