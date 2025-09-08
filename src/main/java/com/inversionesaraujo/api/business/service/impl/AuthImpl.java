package com.inversionesaraujo.api.business.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.AuthResponse;
import com.inversionesaraujo.api.business.payload.GoogleAuthResponse;
import com.inversionesaraujo.api.business.request.GoogleAuthRequest;
import com.inversionesaraujo.api.business.request.LoginRequest;
import com.inversionesaraujo.api.business.request.RegisterRequest;
import com.inversionesaraujo.api.business.service.IAuth;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.Image;
import com.inversionesaraujo.api.model.ImageType;
import com.inversionesaraujo.api.model.InvoiceClientDetail;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.ClientRepository;
import com.inversionesaraujo.api.repository.InvoiceClientDetailRepository;
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
    private InvoiceClientDetailRepository invoiceClientDetailRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private RoleImpl roleService;
    @Autowired
    private FirebaseAuth firebaseAuth;

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

    @Override
    public AuthResponse registerWithGoogle(GoogleAuthRequest request) {
        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(request.getToken());
            String email = decodedToken.getEmail();
            
            if (email == null || email.isEmpty()) {
                throw new RuntimeException("No se pudo obtener el correo electrónico del token de Google");
            }
            
            Optional<User> userOpt = userRepo.findByUsername(email);

            if (userOpt.isPresent()) {
                throw new RuntimeException("El usuario ya existe");
            }

            Client newClient = Client.builder()
                .email(email)
                .rsocial(request.getRsocial())
                .documentType(request.getDocumentType())
                .document(request.getDocument())
                .build();

            newClient = clientRepo.save(newClient);

            InvoiceClientDetail newInvoiceClientDetail = InvoiceClientDetail.builder()
                .client(newClient)
                .document(request.getDocument())
                .rsocial(request.getRsocial())
                .address("-")
                .documentType(request.getDocumentType())
                .invoicePreference(request.getInvoicePreference())
                .build();

            newInvoiceClientDetail = invoiceClientDetailRepo.save(newInvoiceClientDetail);

            RoleDTO role = roleService.findByName("CLIENTE");

            Image profileImage = null;
            if (decodedToken.getPicture() != null && !decodedToken.getPicture().isEmpty()) {
                profileImage = Image.builder()
                    .url(decodedToken.getPicture())
                    .firebaseId("GOOGLE_" + decodedToken.getUid())
                    .type(ImageType.GOOGLE_PROFILE)
                    .build();
            }

            User newUser = User.builder()
                .client(newClient)
                .username(email)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .role(RoleDTO.toEntity(role))
                .image(profileImage)
                .build();

            newUser = userRepo.save(newUser);

            String token = jwtService.getToken(newUser);

            return AuthResponse.builder()
                .token(token)
                .user(UserDTO.toDTO(newUser))
                .build();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al autenticar con Google", e);
        }
    }

    @Override
    public GoogleAuthResponse loginWithGoogle(GoogleAuthRequest request) {
        try {
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(request.getToken());
            String email = decodedToken.getEmail();
            
            if (email == null || email.isEmpty()) {
                throw new RuntimeException("No se pudo obtener el correo electrónico del token de Google");
            }
            
            Optional<User> userOpt = userRepo.findByUsername(email);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String token = jwtService.getToken(user);
                
                return GoogleAuthResponse.builder()
                    .token(token)
                    .user(UserDTO.toDTO(user))
                    .action("login")
                    .build();
            } else {
                String rsocial = decodedToken.getName() != null ? decodedToken.getName() : email.split("@")[0];

                return GoogleAuthResponse.builder()
                    .action("register")
                    .email(email)
                    .token(request.getToken())
                    .rsocial(rsocial)
                    .build();
            }
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al autenticar con Google", e);
        }
    }
}
