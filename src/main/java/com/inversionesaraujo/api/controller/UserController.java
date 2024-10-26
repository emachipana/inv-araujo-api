package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.payload.UserResponse;
import com.inversionesaraujo.api.model.request.UserRequest;
import com.inversionesaraujo.api.service.IUser;
import com.inversionesaraujo.api.service.I_Image;

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
    public List<User> getAll() {
        return userService.listAll();
    }

    @GetMapping("profile/info")
    public ResponseEntity<MessageResponse> getUserLogged(Authentication auth) {
        try {
            String username = auth.getName();
            User currentUser = userService.findByUsername(username);
            UserResponse userResponse = UserResponse
                .builder()
                .id(currentUser.getId())
                .image(currentUser.getImage())
                .name(currentUser.getAdmin() != null ? currentUser.getAdmin().getFirstName() : currentUser.getClient().getRsocial())
                .lastName(currentUser.getAdmin() != null ? currentUser.getAdmin().getLastName() : "")
                .role(currentUser.getRole())
                .username(currentUser.getUsername())
                .build();

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El usuario se encontro con exito")
                .data(userResponse)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);
            UserResponse userResponse = UserResponse
                .builder()
                .id(user.getId())
                .image(user.getImage())
                .name(user.getAdmin() != null ? user.getAdmin().getFirstName() : user.getClient().getRsocial())
                .lastName(user.getAdmin() != null ? user.getAdmin().getLastName() : "")
                .role(user.getRole())
                .username(user.getUsername())
                .build();

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El usuario se encontro con exito")
                .data(userResponse)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);
            userService.delete(user);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El usuario se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody UserRequest request) {
        try {
            User user = userService.findById(id);
            Image image = request.getImageId() == null ? null : imageService.findById(request.getImageId());
            user.setImage(image);
            user.setPassword(request.getNewPassword() != null ? passwordEncoder.encode(request.getNewPassword()) : user.getPassword());
            User updatedUser  =userService.save(user);
            
            UserResponse userResponse = UserResponse
                .builder()
                .id(updatedUser.getId())
                .role(updatedUser.getRole())
                .name(user.getAdmin() != null ? user.getAdmin().getFirstName() : user.getClient().getRsocial())
                .lastName(user.getAdmin() != null ? user.getAdmin().getLastName() : "")
                .username(user.getUsername())
                .image(user.getImage())
                .build();
            
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El usuario se actualizo con exito")
                .data(userResponse)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
