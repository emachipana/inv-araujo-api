package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.MessageDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.MessageRequest;
import com.inversionesaraujo.api.business.service.IMessage;
import com.inversionesaraujo.api.model.SortDirection;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private IMessage messageService;

    @GetMapping
    public Page<MessageDTO> getAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "50") Integer size,
        @RequestParam(required = false) SortDirection sort
    ) {
        return messageService.listAll(page, size, sort);
    }

    @GetMapping("search")
    public Page<MessageDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return messageService.search(param, param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        MessageDTO message = messageService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El mensaje se creo con exito")
            .data(message)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid MessageRequest request) {
        MessageDTO newMessage = messageService.save(MessageDTO
            .builder()
            .fullName(request.getFullName())
            .phone(request.getPhone())
            .subject(request.getSubject())
            .content(request.getContent())
            .origin(request.getOrigin())
            .email(request.getEmail())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El mensaje se creo con exito")
            .data(newMessage)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        messageService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El mensaje se elimino con exito")
            .build());
    }
}
