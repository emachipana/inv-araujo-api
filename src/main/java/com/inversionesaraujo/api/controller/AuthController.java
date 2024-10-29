package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Reset;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.AuthResponse;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.payload.ValidCodeResponse;
import com.inversionesaraujo.api.model.request.ChangePasswordRequest;
import com.inversionesaraujo.api.model.request.EmailRequest;
import com.inversionesaraujo.api.model.request.LoginRequest;
import com.inversionesaraujo.api.model.request.RegisterRequest;
import com.inversionesaraujo.api.model.request.SendCodeRequest;
import com.inversionesaraujo.api.model.request.ValidCodeRequest;
import com.inversionesaraujo.api.service.IAuth;
import com.inversionesaraujo.api.service.IEmail;
import com.inversionesaraujo.api.service.IReset;
import com.inversionesaraujo.api.service.IUser;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private IAuth authService;
    @Autowired
    private IUser userService;
    @Autowired
    private IEmail emailService;
    @Autowired
    private IReset resetService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("sendCode")
    public ResponseEntity<MessageResponse> sendCode(@RequestBody SendCodeRequest request) {
        try {
            User user = userService.findByUsername(request.getEmail());
            String code = resetService.generateCode();
            emailService.sendEmail(EmailRequest
                .builder()
                .content("Tu codigo de recuperacion es: " + code)
                .destination(user.getUsername())
                .subject("Recuperación de cuenta | Inversiones Araujo")
                .build());

            LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));

            Reset reset = resetService.save(Reset
                .builder()
                .code(code)
                .user(user)
                .expiresAt(now.plusMinutes(30))
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .data(reset)
                .message("El codigo de recuperacion se genero y se envio con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("validCode")
    public ResponseEntity<MessageResponse> validCode(@RequestBody ValidCodeRequest request) {
        try {
            Reset reset = resetService.findById(request.getResetId());
            Boolean isValid = resetService.validCode(reset, request.getCode());
            ValidCodeResponse response = ValidCodeResponse
                .builder()
                .userId(!isValid ? null : reset.getUser().getId())
                .isValid(isValid)
                .build();

            return new ResponseEntity<>(MessageResponse
                .builder()
                .data(response)
                .message(isValid ? "El codigo es correcto" : "El codigo no es correcto")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("changePassword/{userId}")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordRequest request, @PathVariable Integer userId) {
        try {
            Reset reset = resetService.findById(request.getResetId());
            Boolean isValid = resetService.validCode(reset, request.getCode());
            if(!isValid) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("El código no es correcto")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            User user = userService.findById(userId);
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userService.save(user);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La contraseña se actualizo con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
