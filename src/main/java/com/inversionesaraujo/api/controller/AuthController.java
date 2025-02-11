package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ResetDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.AuthResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.ValidCodeResponse;
import com.inversionesaraujo.api.business.request.ChangePasswordRequest;
import com.inversionesaraujo.api.business.request.EmailRequest;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;
import com.inversionesaraujo.api.business.request.SendCodeRequest;
import com.inversionesaraujo.api.business.request.ValidCodeRequest;
import com.inversionesaraujo.api.business.service.IAuth;
import com.inversionesaraujo.api.business.service.IEmail;
import com.inversionesaraujo.api.business.service.IReset;
import com.inversionesaraujo.api.business.service.IUser;

import jakarta.validation.Valid;

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
    public ResponseEntity<MessageResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse newLogin = authService.login(request);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El inicio de sesion se completo con exito")
            .data(newLogin)
            .build());
    }

    @PostMapping("register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthResponse newRegister = authService.register(request);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro se completo con exito")
            .data(newRegister)
            .build());
    }

    @PostMapping("sendCode")
    public ResponseEntity<MessageResponse> sendCode(@RequestBody @Valid SendCodeRequest request) {
        UserDTO user = userService.findByUsername(request.getEmail());
        String code = resetService.generateCode();
        emailService.sendEmail(EmailRequest
            .builder()
            .content("Tu codigo de recuperacion es: " + code)
            .destination(user.getUsername())
            .subject("Recuperación de cuenta | Inversiones Araujo")
            .build());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));

        ResetDTO reset = resetService.save(ResetDTO
            .builder()
            .code(code)
            .userId(user.getId())
            .expiresAt(now.plusMinutes(30))
            .build());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(reset)
            .message("El codigo de recuperacion se genero y se envio con exito")
            .build());
    }

    @PostMapping("validCode")
    public ResponseEntity<MessageResponse> validCode(@RequestBody @Valid ValidCodeRequest request) {
        ResetDTO reset = resetService.findById(request.getResetId());
        Boolean isValid = resetService.validCode(reset, request.getCode());
        ValidCodeResponse response = ValidCodeResponse
            .builder()
            .userId(!isValid ? null : reset.getUserId())
            .isValid(isValid)
            .build();

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(response)
            .message(isValid ? "El codigo es correcto" : "El codigo no es correcto")
            .build());
    }

    @PutMapping("changePassword/{userId}")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable Long userId) {
        ResetDTO reset = resetService.findById(request.getResetId());
        Boolean isValid = resetService.validCode(reset, request.getCode());
        if(!isValid) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("El código no es correcto")
                .build());
        }

        UserDTO user = userService.findById(userId);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La contraseña se actualizo con exito")
            .build());
    }
}
