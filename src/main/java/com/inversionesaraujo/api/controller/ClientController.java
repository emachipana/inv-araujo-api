package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ClientRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.model.SortDirection;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    private IClient clientService;

    @GetMapping
    public Page<ClientDTO> getAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(required = false) SortDirection sort
    ) {
        return clientService.filterClients(page, size, sort);
    }

    @GetMapping("/search")
    public Page<ClientDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return clientService.search(param, param, param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El client se encontro con exito")
            .data(client)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ClientRequest request) {
        ClientDTO clientToSave = clientService.save(ClientDTO
            .builder()
            .city(request.getCity())
            .department(request.getDepartment())
            .phone(request.getPhone())
            .document(request.getDocument())
            .documentType(request.getDocumentType())
            .rsocial(request.getRsocial())
            .createdBy(request.getCreatedBy())
            .email(request.getEmail())
            .userId(request.getUserId())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El client se creo con exito")
            .data(clientToSave)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid ClientRequest request, @PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        client.setCity(request.getCity());
        client.setDepartment(request.getDepartment());
        client.setPhone(request.getPhone());
        ClientDTO clientUpdated = clientService.save(client);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El cliente se actualizo con exito")
            .data(clientUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        clientService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El cliente se elimino con exito")
            .build());
    }
}
