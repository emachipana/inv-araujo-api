package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.payload.UserResponse;
import com.inversionesaraujo.api.service.IUser;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUser userService;

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
                .lastName(currentUser.getAdmin() != null ? currentUser.getAdmin().getFirstName() : "")
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
                .lastName(user.getAdmin() != null ? user.getAdmin().getFirstName() : "")
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
}
