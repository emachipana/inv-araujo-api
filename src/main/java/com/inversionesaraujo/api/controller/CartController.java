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

import com.inversionesaraujo.api.business.dto.CartDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.business.service.IUser;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {
    @Autowired
    private ICart cartService;
    @Autowired
    private IUser userService;
    
    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        CartDTO cart = cartService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El carrito se encontró con éxito")
            .data(cart)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Long userId) {
        UserDTO user = userService.findById(userId);
        CartDTO newCart = cartService.save(CartDTO
            .builder()
            .userId(user.getId())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El carrito se creó con éxito")
            .data(newCart)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        cartService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El carrito se eliminó con éxito")
            .build());
    }
}
