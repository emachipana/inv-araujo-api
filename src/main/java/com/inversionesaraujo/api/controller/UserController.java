package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.UserRequest;
import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.business.service.I_Image;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUser userService;
    @Autowired
    private I_Image imageService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.listAll();
    }

    @GetMapping("profile/info")
    public ResponseEntity<MessageResponse> getUserLogged(Authentication auth) {
        String username = auth.getName();
        UserDTO currentUser = userService.findByUsername(username);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El usuario se encontro con exito")
            .data(currentUser)
            .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        UserDTO user = userService.findById(id); 

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El usuario se encontro con exito")
            .data(user)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El usuario se elimino con exito")
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        UserDTO user = userService.findById(id);
        ImageDTO image = request.getImageId() == null ? null : imageService.findById(request.getImageId());
        user.setImage(image);

        if(request.getNewPassword() != null) user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        
        UserDTO updatedUser = userService.save(user);
        
        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El usuario se actualizo con exito")
            .data(updatedUser)
            .build());
    }
}
