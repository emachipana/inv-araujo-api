package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.model.User;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {
    @Autowired
    private ICart cartService;
    @Autowired
    private IUser userService;
    
    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Cart cart = cartService.findById(id);
    
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El carrito se encontró con éxito")
                .data(cart)
                .build(), HttpStatus.OK);
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Integer userId) {
        try {
            User user = userService.findById(userId);
            Cart newCart = cartService.save(Cart
                .builder()
                .user(user)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El carrito se creó con éxito")
                .data(newCart)
                .build(), HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Cart cart = cartService.findById(id);
            cartService.delete(cart);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El carrito se eliminó con éxito")
                .build(), HttpStatus.OK);
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
