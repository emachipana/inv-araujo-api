package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.IMessage;
import com.inversionesaraujo.api.model.Message;
import com.inversionesaraujo.api.model.SortDirection;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private IMessage messageService;

    @GetMapping
    public Page<Message> getAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "50") Integer size,
        @RequestParam(required = false) SortDirection sort
    ) {
        return messageService.listAll(page, size, sort);
    }

    @GetMapping("search")
    public List<Message> search(@RequestParam String param) {
        return messageService.search(param, param, param);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Message message = messageService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El mensaje se creo con exito")
                .data(message)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Message message) {
        try {
            Message newMessage = messageService.save(message);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El mensaje se creo con exito")
                .data(newMessage)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Message message = messageService.findById(id);
            messageService.delete(message);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El mensaje se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
