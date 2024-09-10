package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.model.payload.MessageResponse;
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

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            User user = userService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El usuario se encontro con exito")
                .data(user)
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
