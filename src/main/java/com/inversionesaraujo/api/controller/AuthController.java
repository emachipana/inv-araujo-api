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
import com.inversionesaraujo.api.business.payload.GoogleAuthResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.ValidateCodeResponse;
import com.inversionesaraujo.api.business.request.ChangePasswordRequest;
import com.inversionesaraujo.api.business.request.EmailRequest;
import com.inversionesaraujo.api.business.request.GoogleAuthRequest;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;
import com.inversionesaraujo.api.business.request.GenerateCodeRequest;
import com.inversionesaraujo.api.business.request.ValidateCodeRequest;
import com.inversionesaraujo.api.business.service.IAuth;
import com.inversionesaraujo.api.business.service.IEmail;
import com.inversionesaraujo.api.business.service.IReset;
import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.model.GenerateCodeAction;

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
    
    @PostMapping("google")
    public ResponseEntity<MessageResponse> loginWithGoogle(@RequestBody @Valid GoogleAuthRequest request) {
        GoogleAuthResponse authResponse = authService.loginWithGoogle(request);
        
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Inicio de sesión con Google exitoso")
            .data(authResponse)
            .build());
    }

    @PostMapping("google-register")
    public ResponseEntity<MessageResponse> registerWithGoogle(@RequestBody @Valid GoogleAuthRequest request) {
        AuthResponse authResponse = authService.registerWithGoogle(request);
        
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Registro con Google exitoso")
            .data(authResponse)
            .build());
    }

    @PostMapping("generate-code")
    public ResponseEntity<MessageResponse> sendCode(@RequestBody @Valid GenerateCodeRequest request) {
        String email = request.getEmail();

        if(request.getAction() == GenerateCodeAction.RECOVERY_PASSWORD) {
            userService.findByUsername(request.getEmail());
        }else {
            if(userService.existsByUsername(email)) {
                return ResponseEntity.status(406).body(MessageResponse
                    .builder()
                    .message("El email ya esta registrado")
                    .build());
            }
        }

        String content = request.getAction() == GenerateCodeAction.RECOVERY_PASSWORD
            ? "Para recuperar tu contraseña, ingresa el siguiente código: "
            : "Para validar tu email, ingresa el siguiente código: ";

        String subject = request.getAction() == GenerateCodeAction.RECOVERY_PASSWORD
            ? "Recuperación de contraseña | Inversiones Araujo"
            : "Validación de email | Inversiones Araujo";

        String code = resetService.generateCode();
        Boolean isSended = emailService.sendEmail(EmailRequest
            .builder()
            .content(content + code)
            .destination(email)
            .subject(subject)
            .build());

        if(!isSended) {
            return ResponseEntity.status(500).body(MessageResponse
                .builder()
                .message("Hubo un error al enviar el codigo")
                .build());
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));

        ResetDTO reset = resetService.save(ResetDTO
            .builder()
            .code(code)
            .email(email)
            .expiresAt(now.plusMinutes(30))
            .build());

        reset.setCode("******");

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(reset)
            .message("El codigo se genero y se envio con exito")
            .build());
    }

    @PostMapping("validate-code")
    public ResponseEntity<MessageResponse> validateCode(@RequestBody @Valid ValidateCodeRequest request) {
        ResetDTO reset = resetService.findById(request.getResetId());
        Boolean isValid = resetService.validCode(reset, request.getCode());
        ValidateCodeResponse response = ValidateCodeResponse
            .builder()
            .email(isValid ? reset.getEmail() : null)
            .isValid(isValid)
            .build();

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .data(response)
            .message(isValid ? "El codigo es válido" : "El codigo no es válido")
            .build());
    }

    @PutMapping("change-password/{email}")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable String email) {
        ResetDTO reset = resetService.findById(request.getResetId());
        Boolean isValid = resetService.validCode(reset, request.getCode());
        if(!isValid) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("El código no es correcto")
                .build());
        }

        UserDTO user = userService.findByUsername(email);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La contraseña se actualizo con exito")
            .build());
    }
}
